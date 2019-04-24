package com.oss.resource.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oss.resource.service.UploadService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/resource")
public class UploadController {

	private static String GET_API_LINK = "/api/v1/resource/";

	@Autowired
	private UploadService uploadService;

	@Value("${authorization}")
	private String authorization;

	@RequestMapping(method = POST)
	@ApiOperation(value = "add an resource to storage", httpMethod = "POST", response = String.class, notes = "will return the uuid of the resource.")
	public ResourceLink add(@RequestParam(required=false, defaultValue="none") String tags, @RequestParam MultipartFile file,
			// @RequestParam(required = false) String md5,
			@RequestHeader(name = "Authorization") String password, HttpServletRequest request)
			throws FileNotFoundException, IOException {

		if (!authorization.equals(password)) {
			return null;
		}

		String uuid = uploadService.save(tags, file, "");
		return createResourceLink(request, uuid);

	}

	@RequestMapping(value = "/multi", method = RequestMethod.POST)
	@ApiOperation(value = "add multipul resources to storage", httpMethod = "POST", response = String.class, notes = "will return a list contains the uuids of the resources.")
	public List<ResourceLink> addMany(@RequestParam(required=false, defaultValue="none") String tags,  MultipartHttpServletRequest request,
			@RequestHeader(name = "Authorization") String password)
			throws FileNotFoundException, IOException {

		if (!authorization.equals(password)) {
			return null;
		}

		final List<ResourceLink> files = new ArrayList<ResourceLink>();
		// 获取multiRequest 中所有的文件名
		Iterator<String> names = request.getFileNames();

		while (names.hasNext()) {
			String uuid = uploadService.save(tags, request.getFile(names.next()), null);
			files.add(createResourceLink(request, uuid));
		}
		return files;
	}

	private ResourceLink createResourceLink(HttpServletRequest request, String uuid) {
		String uri = request.getRequestURL().toString();
		uri = uri.substring(0, uri.indexOf("/api")) + GET_API_LINK + uuid;
		return new ResourceLink(uuid, uri);
	}

	@RequestMapping(value = "/offline", method = RequestMethod.POST)
	@ApiOperation(value = "add online resources to storage offline", httpMethod = "POST", response = String.class, notes = "从指定的URL下载网络资源到本地. ")
	public ResourceLink uploadOffline(@RequestParam String tags, @RequestParam String url,
			@RequestParam(required = false, defaultValue = MediaType.ALL_VALUE) String contentType,
			@RequestParam(required = false) String referer, HttpServletRequest request)
			throws MalformedURLException, FileNotFoundException, IOException, RuntimeException, URISyntaxException {
		String uuid = uploadService.saveUrlToFile(tags, url, contentType, referer);
		return createResourceLink(request, uuid);
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	public void delete(@PathVariable String id,
			@RequestHeader(name = "Authorization", required = false) String password) {
		uploadService.setIllegal(id, false);
	}

}

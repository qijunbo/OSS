package com.oss.resource;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(method = POST)
	@ApiOperation(value = "add an resource to storage", httpMethod = "POST", response = String.class, notes = "will return the uuid of the resource.")
	public  ResourceLink add(@RequestParam String tags, @RequestParam MultipartFile file,
			@RequestParam(required = false) String md5,
			@RequestHeader(name = "Authorization", required = false) String password)
			throws FileNotFoundException, IOException {
			
		String uuid = uploadService.save(tags, file , md5);
		return new ResourceLink(uuid, GET_API_LINK + uuid);
	}

	@RequestMapping(value = "/multi", method = RequestMethod.POST)
	@ApiOperation(value = "add multipul resources to storage", httpMethod = "POST", response = String.class, notes = "will return a list contains the uuids of the resources.")
	public List<ResourceLink> addMany(@RequestParam String tags, MultipartHttpServletRequest request,
			@RequestHeader(name = "Authorization", required = false) String password)
			throws FileNotFoundException, IOException {
		final List<ResourceLink> files = new ArrayList<ResourceLink>();
		// 获取multiRequest 中所有的文件名
		Iterator<String> names = request.getFileNames();
		while (names.hasNext()) {
			String uuid = uploadService.save(tags, request.getFile(names.next()), null);
			files.add(new ResourceLink(uuid, GET_API_LINK + uuid));
		}
		return files;
	}
	
	
    @RequestMapping(value = "/offline", method = RequestMethod.POST)
    @ApiOperation(value = "add online resources to storage offline", httpMethod = "POST", response = String.class, notes = "从指定的URL下载网络资源到本地. ")
    public ResourceLink uploadOffline(@RequestParam String tags, @RequestParam String url , 
    		@RequestParam(required=false, defaultValue=MediaType.ALL_VALUE) String contentType,
    		@RequestParam(required=false ) String referer ) throws MalformedURLException, FileNotFoundException, IOException, RuntimeException, URISyntaxException   {
        String uuid = uploadService.saveUrlToFile(tags, url, contentType, referer);
        return new ResourceLink(uuid, GET_API_LINK + uuid);
    }

	@RequestMapping(value = "/{id}", method = DELETE)
	public void delete(@PathVariable String id, @RequestHeader(name = "Authorization", required = false) String password) {
		uploadService.setIllegal(id, false);
	}

}

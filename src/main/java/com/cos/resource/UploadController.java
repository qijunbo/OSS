package com.cos.resource;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
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

import com.cos.resource.service.UploadService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/resource")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@RequestMapping(method = POST)
	@ApiOperation(value = "add an resource to storage", httpMethod = "POST", response = String.class, notes = "will return the uuid of the resource.")
	public  String add(@RequestParam String tags, @RequestParam MultipartFile file,
			@RequestParam(required = false) String md5,
			@RequestHeader(name = "Authorization", required = false) String password)
			throws FileNotFoundException, IOException {
		return uploadService.save(tags, file , md5);
	}

	@RequestMapping(value = "/multi", method = RequestMethod.POST)
	@ApiOperation(value = "add multipul resources to storage", httpMethod = "POST", response = String.class, notes = "will return a list contains the uuids of the resources.")
	public List<String> addMany(@RequestParam String tags, MultipartHttpServletRequest request,
			@RequestHeader(name = "Authorization", required = false) String password)
			throws FileNotFoundException, IOException {
		final List<String> files = new ArrayList<String>();
		// 获取multiRequest 中所有的文件名
		Iterator<String> names = request.getFileNames();
		while (names.hasNext()) {
			files.add(uploadService.save(tags, request.getFile(names.next()), null));
		}
		return files;
	}
	
	
    @RequestMapping(value = "/offline", method = RequestMethod.POST)
    @ApiOperation(value = "add online resources to storage offline", httpMethod = "POST", response = String.class, notes = "will return a list contains the uuids of the resources.")
    public String uploadOffline(@RequestParam String tags, @RequestParam String url , 
    		@RequestParam(required=false, defaultValue=MediaType.ALL_VALUE) String contentType) throws MalformedURLException, FileNotFoundException, IOException   {
        return uploadService.saveUrlImage(tags, url, contentType);
    }

	@RequestMapping(value = "/{id}", method = DELETE)
	public void delete(@PathVariable String id, @RequestHeader(name = "Authorization", required = false) String password) {
		uploadService.setIllegal(id, false);
	}

}

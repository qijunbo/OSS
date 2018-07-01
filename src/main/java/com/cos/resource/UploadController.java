package com.cos.resource;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.resource.repository.Resource;
import com.cos.resource.service.UploadService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/resource")
public class UploadController {

	
	@Autowired
	private UploadService uploadService;

	
	@RequestMapping(method = POST)
	@ApiOperation(value = "add an resource to storage", httpMethod = "POST", response = String.class, notes = "will return the uuid of the resource.")
	public @ResponseBody String add(@RequestParam String tags, 
			@RequestParam MultipartFile file, 
			@RequestHeader("Authorization") String password ) throws FileNotFoundException, IOException {
		return uploadService.save(tags, file);
	}

	@RequestMapping(value = "/multi", method = RequestMethod.POST)
	@ApiOperation(value = "add multipul resources to storage", httpMethod = "POST", response = String.class, notes = "will return a list contains the uuids of the resources.")
	public @ResponseBody List<String> addMany(@RequestBody Resource Resource) {

 
		return null;
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	public @ResponseBody void delete(@PathVariable long id) {

	}

}

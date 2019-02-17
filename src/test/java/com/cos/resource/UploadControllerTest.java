package com.cos.resource;

import java.io.File;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
/**
 * 
 * @see https://blog.csdn.net/mhmyqn/article/details/26395743
 * @author qijunbo
 *
 */
public class UploadControllerTest {
	
	//@Test
	public void testUpload() throws Exception {
		
		String url = "http://localhost:1001/api/v1/resource?tags=test";
		String filePath = "C:/Users/qijunbo/Pictures/ELN/1.png";
 
		RestTemplate rest = new RestTemplate();
		FileSystemResource resource = new FileSystemResource(new File(filePath));
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("file", resource);
		param.add("tags", "简谱");
 
		String uuid = rest.postForObject(url, param, String.class);
		System.out.println(uuid);
		
		
	}
 

}

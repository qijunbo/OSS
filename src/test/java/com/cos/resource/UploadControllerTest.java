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
	
	@Test
	public void testUpload() throws Exception {
		String url = "http://192.168.1.30:8888/central/api/v1/resource?tags=test";
		String filePath = "c:\\workspace\\Activiti-master.zip";
 
		RestTemplate rest = new RestTemplate();
		FileSystemResource resource = new FileSystemResource(new File(filePath));
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("file", resource);
		param.add("tags", "简谱");
 
		String string = rest.postForObject(url, param, String.class);
		System.out.println(string);
	}
 

}

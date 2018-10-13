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
		String url = "http://127.0.0.1:80/api/v1/resource";
		String filePath = "D:\\女儿情.jpg";
 
		RestTemplate rest = new RestTemplate();
		FileSystemResource resource = new FileSystemResource(new File(filePath));
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("file", resource);
		param.add("tags", "简谱");
 
		String string = rest.postForObject(url, param, String.class);
		System.out.println(string);
	}
 

}

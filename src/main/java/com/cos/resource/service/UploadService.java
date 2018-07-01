package com.cos.resource.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	public String save(String tags, MultipartFile file) throws FileNotFoundException, IOException;

	public String saveUrlImage(String tags, String url, String contentType) throws MalformedURLException, FileNotFoundException, IOException;

}

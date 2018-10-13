package com.cos.resource.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.cos.resource.repository.Resource;
import com.cos.resource.repository.ResourceRepository;

@Service
public class DownloadServiceImpl implements DownloadService {

	@Autowired
	private ResourceRepository resourceRepository;

	@Value("${storage.root:/home/ftpuser/}")
	private String root;

	@Override
	public ResponseEntity<InputStreamResource> getFileResource(String id) throws FileNotFoundException {

		Resource fileInfo = resourceRepository.findOne(id);
		if (fileInfo == null) {
			throw new FileNotFoundException("File is not found! ");
		}
		File file = new File(root + fileInfo.getPath());

		// 存在并合法
		if (file.exists() && fileInfo.isLegal()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=\"" + fileInfo.getName() + "\"");
			ResponseEntity<InputStreamResource> responseEntity = new ResponseEntity<InputStreamResource>(
					new InputStreamResource(new FileInputStream(file)), headers, HttpStatus.OK);
			return responseEntity;
		} else {
			throw new FileNotFoundException("File is not found or illegal!" + file.getAbsolutePath());
		}

	}

	@Override
	public void writeToResponse(String id,  HttpServletResponse response)
			throws IOException {
		Resource fileInfo = resourceRepository.findOne(id);
		if (fileInfo == null) {
			throw new FileNotFoundException("File is not found! ");
		}
		File file = new File(root + fileInfo.getPath());

		// 存在并合法
		if (file.exists() && fileInfo.isLegal()) {
			if (MediaType.ALL_VALUE.equals(response.getContentType()) || response.getContentType() == null) {
				response.setContentType(fileInfo.getMimeType());
			}
			response.setHeader("Content-Disposition", "filename=\"" + fileInfo.getName() + "\"");
			// StreamUtils always close the input and output stream after finish the job.
			// so, please set headers before this.
			StreamUtils.copy(new FileInputStream(file), response.getOutputStream());
		} else {
			throw new FileNotFoundException("File is not found or illegal!" + file.getAbsolutePath());
		}

	}
}

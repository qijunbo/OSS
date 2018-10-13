package com.cos.resource.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface DownloadService {
 
    public ResponseEntity<InputStreamResource> getFileResource(String id) throws FileNotFoundException ;
    
    public void writeToResponse(String id, HttpServletResponse response) throws IOException;
    
}

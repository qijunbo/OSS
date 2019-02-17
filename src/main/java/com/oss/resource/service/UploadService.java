package com.oss.resource.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	/**
	 * 
	 * @param tags  资源标签, 用于做搜索
	 * @param file  待保存资源
	 * @param md5   md5码
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String save(String tags, MultipartFile file, String md5) throws FileNotFoundException, IOException;

	/**
	 * 
	 * @param tags  源标签, 用于做搜索
	 * @param url  在线资源的url
	 * @param contentType 资源类型
	 * @param referer 资源的出处, 用于下载防盗链的资源
	 * @return
	 * @throws MalformedURLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @throws UnsupportedOperationException 
	 */
	public String saveUrlToFile(String tags, String url, String contentType, String referer) throws IOException, RuntimeException, URISyntaxException ;

	/**
	 * 用于标注侵权,违规资源
	 * @param id
	 * @param legal
	 */
	public void setIllegal(String id, boolean legal);

}

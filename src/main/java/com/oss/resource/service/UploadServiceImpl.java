package com.oss.resource.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.oss.config.PathConfig;
import com.oss.resource.repository.Resource;
import com.oss.resource.repository.ResourceRepository;

@Service
public class UploadServiceImpl implements UploadService {

	private static final String OTHER_TYPE = "other";

	public static final String STANDARD_DATE_PATTERN = "yyyy-MM-dd";

	public static final SimpleDateFormat DefaultDateFormat = new SimpleDateFormat(STANDARD_DATE_PATTERN);

	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private PathConfig config;
 
	@Override
	public String save(String tags, MultipartFile file, String md5) throws FileNotFoundException, IOException {
		// save file to disk
		String originName = file.getOriginalFilename();
		String contentType = file.getContentType();
		InputStream inputStream = file.getInputStream();
		return saveResource(tags, originName, inputStream, contentType, md5);
	}

	@Override
	public String saveUrlToFile(String tags, String url, String contentType, String referer)
			throws IOException, RuntimeException, URISyntaxException{

		int index = url.lastIndexOf('/');
		String originName = index > 0 ? url.substring(index) : url;
		File targetFile = createFile(originName);
		FileDownloadUtil.downloadImage(url, targetFile, referer);
		Resource resource = new Resource(originName, tags, contentType);
		resource.setPath(targetFile.getAbsolutePath().replace(config.getRoot(), ""));
		return resourceRepository.save(resource).getId();
	}

	private String saveResource(String tags, String originName, InputStream inputStream, String contentType,
			String md5code) throws IOException, FileNotFoundException {

		File targetFile = createFile(originName);
		FileCopyUtils.copy(inputStream, new FileOutputStream(targetFile));
		Resource resource = new Resource(originName, tags, contentType);
		resource.setPath(targetFile.getAbsolutePath().replace(config.getRoot(), ""));
		return resourceRepository.save(resource).getId();
		
		//md5code = DigestUtils.md5DigestAsHex(new FileInputStream(targetFile));
		//Resource resource = resourceRepository.findByMd5code(md5code);
	}

	private File createFile(String originName) {
		int index = originName.lastIndexOf('.');

		// 通过扩展名获取文件类型
		String extname = index > 0 ? originName.substring(index + 1).toLowerCase() : OTHER_TYPE;
		// 生成保存路径， 如果上传文件无扩展名，就不要扩展名
		String fullName = config.getRoot() + extname + File.separator + DefaultDateFormat.format(new Date()) + File.separator
				+ UUID.randomUUID().toString() + (OTHER_TYPE.equals(extname) ? "" : "." + extname);
		File targetFile = new File(fullName);

		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		return targetFile;
	}

	@Override
	public void setIllegal(String id, boolean legal) {
		Resource resource = resourceRepository.findOne(id);
		if (resource != null) {
			resource.setLegal(legal);
			resourceRepository.save(resource);
		}
	}

}

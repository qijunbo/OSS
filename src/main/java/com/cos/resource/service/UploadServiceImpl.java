package com.cos.resource.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cos.resource.repository.Resource;
import com.cos.resource.repository.ResourceRepository;

@Service
public class UploadServiceImpl implements UploadService {

	private static final String OTHER_TYPE = "other";

	public static final String STANDARD_DATE_PATTERN = "yyyy-MM-dd";

	public static final SimpleDateFormat DefaultDateFormat = new SimpleDateFormat(STANDARD_DATE_PATTERN);

	@Value("${storage.root:/home/ftpuser/}")
	private String root;

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public String save(String tags, MultipartFile file) throws FileNotFoundException, IOException {
		// save file to disk
		String originName = file.getOriginalFilename();
		String contentType = file.getContentType();
		InputStream inputStream = file.getInputStream();
		return saveResource(tags, originName, inputStream, contentType);

	}

	@Override
	public String saveUrlImage(String tags, String url, String contentType)
			throws MalformedURLException, FileNotFoundException, IOException {

		int index = url.lastIndexOf('/');
		String originName = index > 0 ? url.substring(index) : url;
		InputStream inputStream = new URL(url).openStream();
		return saveResource(tags, originName, inputStream, contentType);
	}

	private String saveResource(String tags, String originName, InputStream inputStream, String contentType)
			throws IOException, FileNotFoundException {
		File targetFile = createFile(originName);
		StreamUtils.copy(inputStream, new FileOutputStream(targetFile));

		String md5code = DigestUtils.md5DigestAsHex(new FileInputStream(targetFile));
		Resource resource = resourceRepository.findByMd5code(md5code);
		if (resource != null) {
			// file exists.
			targetFile.delete();
			return resource.getId();
		} else {
			// save information to database
			resource = new Resource(originName, tags, contentType);
			resource.setMd5code(md5code);
			resource.setPath(targetFile.getAbsolutePath().replace(root, ""));
			return resourceRepository.save(resource).getId();
		}
	}

	private File createFile(String originName) {
		int index = originName.lastIndexOf('.');

		// 通过扩展名获取文件类型
		String extname = index > 0 ? originName.substring(index + 1).toLowerCase() : OTHER_TYPE;
		// 生成保存路径， 如果上传文件无扩展名，就不要扩展名
		String fullName = root + extname + File.separator + DefaultDateFormat.format(new Date()) + File.separator
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

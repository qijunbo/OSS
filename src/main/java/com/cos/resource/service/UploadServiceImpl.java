package com.cos.resource.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cos.resource.repository.Resource;
import com.cos.resource.repository.ResourceRepository;

@Service
public class UploadServiceImpl implements UploadService {

	private static final String OTHER_TYPE = "other";

	public static final String STANDARD_DATE_PATTERN = "yyyy-MM-dd";

	public static final SimpleDateFormat DefaultDateFormat = new SimpleDateFormat(STANDARD_DATE_PATTERN);

	@Value("${storage.root.linux:/home/ftpuser/}")
	private String rootlinux;

	@Value("${storage.root.windows:d:/download/}")
	private String rootwindows;

	@Autowired
	private ResourceRepository resourceRepository;

	private String getRoot() {
		String path = SystemUtils.IS_OS_WINDOWS ? rootwindows : rootlinux;
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		return path;
	}

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
		DownloadFileUtil.downloadImage(url, targetFile, referer);
		Resource resource = new Resource(originName, tags, contentType);
		resource.setMd5code("");
		resource.setPath(targetFile.getAbsolutePath().replace(getRoot(), ""));
		return resourceRepository.save(resource).getId();
	}

	private String saveResource(String tags, String originName, InputStream inputStream, String contentType,
			String md5code) throws IOException, FileNotFoundException {

		// in case of good shoot, return very quickly.
		if (StringUtils.isNotBlank(md5code) && md5code.length() > 5) {
			Resource resource = resourceRepository.findByMd5code(md5code);
			if (resource != null) {
				return resource.getId();
			}
		}

		File targetFile = createFile(originName);
		FileCopyUtils.copy(inputStream, new FileOutputStream(targetFile));

		md5code = DigestUtils.md5DigestAsHex(new FileInputStream(targetFile));
		Resource resource = resourceRepository.findByMd5code(md5code);
		if (resource != null) {
			// file exists.
			targetFile.delete();
			return resource.getId();
		} else {
			// save information to database
			resource = new Resource(originName, tags, contentType);
			resource.setMd5code(md5code);
			resource.setPath(targetFile.getAbsolutePath().replace(getRoot(), ""));
			return resourceRepository.save(resource).getId();
		}

	}

	private File createFile(String originName) {
		int index = originName.lastIndexOf('.');

		// 通过扩展名获取文件类型
		String extname = index > 0 ? originName.substring(index + 1).toLowerCase() : OTHER_TYPE;
		// 生成保存路径， 如果上传文件无扩展名，就不要扩展名
		String fullName = getRoot() + extname + File.separator + DefaultDateFormat.format(new Date()) + File.separator
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

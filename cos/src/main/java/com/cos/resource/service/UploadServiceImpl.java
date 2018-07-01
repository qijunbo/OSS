package com.cos.resource.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
		File targetFile = createFile(file.getOriginalFilename());
		StreamUtils.copy(file.getInputStream(), new FileOutputStream(targetFile));

		String md5code = DigestUtils.md5DigestAsHex(new FileInputStream(targetFile));

		Resource resource = resourceRepository.findByMd5code(md5code);
		if (resource != null) {
			// file exists.
			targetFile.delete();
			return resource.getId();
		} else {

			// save information to database
			resource = new Resource(file.getOriginalFilename(), tags, file.getContentType());
			resource.setMd5code(md5code);
			resource.setPath(targetFile.getAbsolutePath());
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

}

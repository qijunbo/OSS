package com.oss.config;

import java.io.File;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathConfig {

	@Value("${storage.root.linux:/home/ftpuser/}")
	private String rootlinux;

	@Value("${storage.root.windows:d:\\download\\}")
	private String rootwindows;

	public String getRoot() {
		String path = SystemUtils.IS_OS_WINDOWS ? rootwindows : rootlinux;
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		return path;
	}
}

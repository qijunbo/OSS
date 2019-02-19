package com.oss.resource.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.FileCopyUtils;

public class FileDownloadUtil {
	public static void downloadImage(String uri, File target) throws FileNotFoundException, IOException {

		URL url = new URL(uri);
		FileCopyUtils.copy(url.openStream(), new FileOutputStream(target));

	}

	public static void downloadImage(String url, File target, String referer)
			throws UnsupportedOperationException, FileNotFoundException, IOException, URISyntaxException {

		// 获取连接客户端工具
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			/*
			 * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
			 */
			URIBuilder uriBuilder = new URIBuilder(url);
			/** 第一种添加参数的形式 */
			/*
			 * uriBuilder.addParameter("name", "root");
			 * uriBuilder.addParameter("password", "123456");
			 */
			/** 第二种添加参数的形式 */
			List<NameValuePair> list = new LinkedList<>();
			BasicNameValuePair param1 = new BasicNameValuePair("name", "root");
			BasicNameValuePair param2 = new BasicNameValuePair("password", "123456");
			list.add(param1);
			list.add(param2);
			uriBuilder.setParameters(list);

			// 根据带参数的URI对象构建GET请求对象
			// HttpGet httpGet = new HttpGet(uriBuilder.build());
			HttpGet httpGet = new HttpGet(url);
			/*
			 * 添加请求头信息
			 */
			// 浏览器表示
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
			// 传输的类型
			// httpGet.addHeader("Content-Type",
			// "application/x-www-form-urlencoded");

			httpGet.addHeader("Accept", "*/*");
			httpGet.addHeader("referer", referer);
			// 执行请求
			response = httpClient.execute(httpGet);
			// 获得响应的实体对象
			HttpEntity entity = response.getEntity();
			// 使用Apache提供的工具类进行转换成字符串
			// entityStr = EntityUtils.toString(entity, "UTF-8");
			FileCopyUtils.copy(entity.getContent(), new FileOutputStream(target));
		} finally {
			httpClient.close();
		}
 
	}

}

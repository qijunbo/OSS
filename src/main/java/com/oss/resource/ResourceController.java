package com.oss.resource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oss.resource.repository.Resource;
import com.oss.resource.repository.ResourceRepository;
import com.oss.resource.service.DownloadService;

@Controller
@RequestMapping("/api/v1/resource")
public class ResourceController {

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private DownloadService downloadService;

	/**
	 * @param id
	 *            the identifier of the charge point
	 * @return charge point
	 */
	@RequestMapping(value = "/info/{id}", method = GET)
	public @ResponseBody Resource getInfo(@PathVariable String id) {
		return resourceRepository.findOne(id);
	}

	/**
	 * @param id
	 *            the identifier of the resource
	 * @return charge point
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/{id}", method = GET)
	public ResponseEntity<InputStreamResource> get(@PathVariable String id) throws FileNotFoundException {
		return downloadService.getFileResource(id);
	}

	/**
	 * @param id
	 *            the identifier of the resource
	 * @return charge point
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/image/{id}", method = GET, produces = { MediaType.IMAGE_JPEG_VALUE,  MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
	public void getImage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		downloadService.writeToResponse(id, response);
	}

}

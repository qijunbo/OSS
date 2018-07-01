package com.cos.resource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.resource.repository.Resource;
import com.cos.resource.repository.ResourceRepository;

@Controller
@RequestMapping("/api/v1/resource")
public class ResourceController {

	
	@Autowired
	private ResourceRepository resourceRepository;
	
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
	 *            the identifier of the charge point
	 * @return charge point
	 */
	@RequestMapping(value = "/{id}", method = GET)
	public  void get(@PathVariable String id) {

	}

	


}

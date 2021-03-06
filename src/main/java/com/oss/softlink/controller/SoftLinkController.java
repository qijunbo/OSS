package com.oss.softlink.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oss.softlink.repository.SoftLink;
import com.oss.softlink.repository.SoftLinkRepository;

@RestController
@RequestMapping("/api/v1/softlink")
public class SoftLinkController {

	@Autowired
	private SoftLinkRepository linkRepository;

	/**
	 * @param id
	 *            the identifier of the resource
	 * @return charge point
	 * @throws FileNotFoundException
	 */
	@RequestMapping(method = GET)
	public  Page<SoftLink> get(@RequestHeader(name = "user", required = false) String user)
			throws FileNotFoundException {
		return linkRepository.findAll(new PageRequest(0, 10));
	}

	@RequestMapping(method = POST)
	public String create(@RequestHeader(name = "user", required = false) String user, Integer days)
			throws FileNotFoundException {
		return "";
	}
	
	
}

package com.auto.api.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.auto.api.service.MockPostService;

@RestController
@RequestMapping(value="/post")
public class MockPostController {
	
	@Autowired
	MockPostService mockPostService;
	
	@PostMapping(value = "/{str1}/**")
	public Object create(
	  @PathVariable String str1, HttpServletRequest request, @RequestBody Object body) { 
		
		String restOfTheUrl = (String) request.getAttribute(
			    HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		Map<String, String[]> para = request.getParameterMap();
		if(para.size()>0) {
			restOfTheUrl+="?";
		}
		for(String key: para.keySet()) {
			restOfTheUrl += key+"=" + StringUtils.join(para.get(key))+"&";
		}
	    return mockPostService.handlePost(restOfTheUrl, body);
	}
	
	@PutMapping(value = "/{str1}/**")
	public Object edit(
	  @PathVariable String str1, HttpServletRequest request, @RequestBody Object body) { 
		
		String restOfTheUrl = (String) request.getAttribute(
			    HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		Map<String, String[]> para = request.getParameterMap();
		if(para.size()>0) {
			restOfTheUrl+="?";
		}
		for(String key: para.keySet()) {
			restOfTheUrl += key+"=" + StringUtils.join(para.get(key))+"&";
		}
	    return mockPostService.handlePut(restOfTheUrl, body);
	}
	

}



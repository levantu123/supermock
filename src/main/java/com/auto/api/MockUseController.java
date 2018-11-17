package com.auto.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

@RestController
@RequestMapping(value="/v1")
public class MockUseController {
	
	@Autowired
	MockGetService mockGetService;
	
	@Autowired
	MockPostService mockPostService;
	
	@GetMapping(value = "/{str1}/**")
	public Object get(
	  @PathVariable String str1, HttpServletRequest request) { 
		
		String restOfTheUrl = (String) request.getAttribute(
			    HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		Map<String, String[]> para = request.getParameterMap();
		if(para.size()>0) {
			restOfTheUrl+="?";
		}
		for(String key: para.keySet()) {
			restOfTheUrl += key+"=" + StringUtils.join(para.get(key))+"&";
		}
	    return mockGetService.handleGet(restOfTheUrl, "GET");
	}
	
	@PostMapping(value = "/{str1}/**")
	public Object post(
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
	    return mockPostService.handleGet(restOfTheUrl, "GET", body);
	}
	
}



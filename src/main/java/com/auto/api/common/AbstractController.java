package com.auto.api.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.HandlerMapping;

import com.auto.api.model.Request;

public abstract class AbstractController<M extends AbstractConfig, T extends AbstractService<M, O, A>, O extends Request, A extends AbstractRepository<O>> {
	
	@Autowired
	T abstractService;
	
	@Autowired
	M serviceConfig;
	
	@PostMapping(value = "/{str1}/**")
	public Object create(
	  @PathVariable String str1, HttpServletRequest request, @RequestBody Object body) { 
		
		String restOfTheUrl = (String) request.getAttribute(
			    HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		Map<String, String[]> para = request.getParameterMap();
	    return abstractService.handlePost(restOfTheUrl, para, body);
	}
	
	@PutMapping(value = "/{str1}/**")
	public Object edit(
	  @PathVariable String str1, HttpServletRequest request, @RequestBody Object body) { 
		
		String restOfTheUrl = (String) request.getAttribute(
			    HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		Map<String, String[]> para = request.getParameterMap();
	    return abstractService.handlePut(restOfTheUrl, para, body);
	}
	
}

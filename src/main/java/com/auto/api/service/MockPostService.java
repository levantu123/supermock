package com.auto.api.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auto.api.MockConfigs;
import com.auto.api.common.TextAnalyzer;
import com.auto.api.model.Request;
import com.auto.api.repo.RequestRepository;

@Service
public class MockPostService {
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	TextAnalyzer textAnalyzer;
	
	@Autowired
	private MockConfigs cockConfigs;
	
	
	public Object handleGet(String path, Map<String, String[]> para, String method, Object body) {
		String url = textAnalyzer.buildUrl(path, para);
		String link = textAnalyzer.removePrefixUrl(url, "v1");
		String id = textAnalyzer.buildId(link);
		
		Optional<Request> request = requestRepository.findById(id);
		if(cockConfigs.isOriginLoad()&& cockConfigs.isOriginSave()&& !request.isPresent()) {
			Object map = new RestTemplate().postForEntity(cockConfigs.getOriginUrl()+link, body, Object.class).getBody();
			handlePost(url.replace("v1", "post"), para, map);
			return map;
		}
		if(cockConfigs.isOriginLoad()&& cockConfigs.isOriginSave()&& request.isPresent()) {
			Object map = new RestTemplate().postForEntity(cockConfigs.getOriginUrl()+link, body, Object.class).getBody();
			handlePut(url.replace("v1", "post"), para, map);
			return map;
		}
		if(cockConfigs.isOriginLoad()) {
			return new RestTemplate().postForEntity(cockConfigs.getOriginUrl()+link, body, Object.class).getBody();
		}
		if (request.isPresent()) {
			return request.get().getBodyPost();
		}
		return null;
	}
	
	
	public Object handlePost(String path, Map<String, String[]> para, Object body) {
		String url = textAnalyzer.buildUrl(path, para);
		String link = textAnalyzer.removePrefixUrl(url, "post");
		String id = textAnalyzer.buildId(link);
		if (requestRepository.findById(id).isPresent()) {
			Request req = requestRepository.findById(id).get();
			if(req.getBodyPost()!= null) {
				return req;
			}
			req.setBodyPost(body);
			return requestRepository.save(req);
		}
		
		Request request = new Request();
		request.setRequestId(id);
		request.setBodyPost(body);;
		request.setLink(link);
		return requestRepository.save(request);
	}
	
	public Object handlePut(String path, Map<String, String[]> para, Object body) {
		String url = textAnalyzer.buildUrl(path, para);
		String link = textAnalyzer.removePrefixUrl(url, "post");
		String id = textAnalyzer.buildId(link);
		
		if(requestRepository.findById(id).isPresent()) {
			Request request = requestRepository.findById(id).get();
			request.setBodyPost(body);
			return requestRepository.save(request);
		}
		return null;
	}
}

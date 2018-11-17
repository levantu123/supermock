package com.auto.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MockPostService {
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	private MockConfigs cockConfigs;
	
	
	public Object handleGet(String path, String method, Object body) {
		String id = "";
		String link = path.replace("/v1", "");
		String[] terms = link.split("/");
		for(String term : terms) {
			id += term+"-_";
		}
		Optional<Request> request = requestRepository.findById(id);
		if(cockConfigs.isOriginLoad()&& cockConfigs.isOriginSave()&& !request.isPresent()) {
			Object map = new RestTemplate().postForEntity(cockConfigs.getOriginUrl()+link, body, Object.class).getBody();
			handlePost(path.replace("v1", "post"), map);
			return map;
		}
		if(cockConfigs.isOriginLoad()&& cockConfigs.isOriginSave()&& request.isPresent()) {
			Object map = new RestTemplate().postForEntity(cockConfigs.getOriginUrl()+link, body, Object.class).getBody();
			handlePut(path.replace("v1", "post"), map);
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
	
	
	public Object handlePost(String path, Object body) {
		String newId = "";
		String link = path.replace("/post", "");
		String[] terms = link.split("/");
		for(String term : terms) {
			newId += term+"-_";
		}
		if (requestRepository.findById(newId).isPresent()) {
			Request req = requestRepository.findById(newId).get();
			if(req.getBodyPost()!= null) {
				return req;
			}
			req.setBodyPost(body);
			return requestRepository.save(req);
		}
		
		Request request = new Request();
		request.setRequestId(newId);
		request.setBodyPost(body);;
		request.setLink(link);
		return requestRepository.save(request);
	}
	
	public Object handlePut(String path, Object body) {
		String newId = "";
		String link = path.replace("/post", "");
		String[] terms = link.split("/");
		
		for(String term : terms) {
			newId += term+"-_";
		}
		if(requestRepository.findById(newId).isPresent()) {
			Request request = requestRepository.findById(newId).get();
			request.setBodyPost(body);
			return requestRepository.save(request);
		}
		return null;
	}
}

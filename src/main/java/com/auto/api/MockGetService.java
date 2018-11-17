package com.auto.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MockGetService {
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	private MockConfigs cockConfigs;
	
	
	public Object handleGet(String path, String method) {
		String id = "";
		String link = path.replace("/v1", "");
		String[] terms = link.split("/");
		for(String term : terms) {
			id += term+"-_";
		}
		Optional<Request> request = requestRepository.findById(id);
		if(cockConfigs.isOriginLoad()&& cockConfigs.isOriginSave()&& !request.isPresent()) {
			Object map = new RestTemplate().getForEntity(cockConfigs.getOriginUrl()+link, Object.class).getBody();
			handlePost(path.replace("v1", "get"), map);
			return map;
		}
		if(cockConfigs.isOriginLoad()&& cockConfigs.isOriginSave()&& request.isPresent()) {
			Object map = new RestTemplate().getForEntity(cockConfigs.getOriginUrl()+link, Object.class).getBody();
			handlePut(path.replace("v1", "get"), map);
			return map;
		}
		if(cockConfigs.isOriginLoad()) {
			return new RestTemplate().getForEntity(cockConfigs.getOriginUrl()+link, Object.class).getBody();
		}
		if (request.isPresent()) {
			return request.get().getBodyGet();
		}
		return null;
	}
	
	
	public Object handlePost(String path, Object body) {
		String newId = "";
		String link = path.replace("/get", "");
		String[] terms = link.split("/");
		for(String term : terms) {
			newId += term+"-_";
		}
		if (requestRepository.findById(newId).isPresent()) {
			Request req = requestRepository.findById(newId).get();
			if(req.getBodyGet()!= null) {
				return req;
			}
			req.setBodyGet(body);
			return requestRepository.save(req);
		}
		
		Request request = new Request();
		request.setRequestId(newId);
		request.setBodyGet(body);;
		request.setLink(link);
		return requestRepository.save(request);
	}
	
	public Object handlePut(String path, Object body) {
		String newId = "";
		String link = path.replace("/get", "");
		String[] terms = link.split("/");
		
		for(String term : terms) {
			newId += term+"-_";
		}
		if(requestRepository.findById(newId).isPresent()) {
			Request request = requestRepository.findById(newId).get();
			request.setBodyGet(body);
			return requestRepository.save(request);
		}
		return null;
		
	}
}

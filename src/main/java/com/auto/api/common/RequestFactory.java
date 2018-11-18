package com.auto.api.common;

import org.springframework.stereotype.Service;

import com.auto.api.model.Request;

@Service
public class RequestFactory {
	public Request initRequest(String id, Object body, String link) {
		Request request = new Request();
		request.setRequestId(id);
		request.setBodyGet(body);;
		request.setLink(link);
		return request;
	}
}

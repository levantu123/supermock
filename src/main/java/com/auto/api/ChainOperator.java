package com.auto.api;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChainOperator {
	private RestTemplate restTemplate;
	
	public void put() {
		restTemplate.postForEntity("", "", Object.class);
	}
	
}

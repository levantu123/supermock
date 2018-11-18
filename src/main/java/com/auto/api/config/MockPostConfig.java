package com.auto.api.config;

import org.springframework.web.client.RestTemplate;

import com.auto.api.common.AbstractConfig;

public class MockPostConfig extends AbstractConfig{

	@Override
	public String getPrefix() {
		return "post";
	}

	@Override
	public Object getFromRealApi(String originUrl, String link, Object body) {
		return new RestTemplate().postForEntity(originUrl+link, body, Object.class).getBody();
	}

}

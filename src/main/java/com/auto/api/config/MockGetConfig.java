package com.auto.api.config;

import org.springframework.web.client.RestTemplate;

import com.auto.api.common.AbstractConfig;

public class MockGetConfig extends AbstractConfig{

	@Override
	public String getPrefix() {
		return "get";
	}

	@Override
	public Object getFromRealApi(String originUrl, String link, Object body) {
		return new RestTemplate().getForEntity(originUrl+link, Object.class).getBody();
	}

}

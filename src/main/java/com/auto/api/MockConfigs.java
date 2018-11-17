package com.auto.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties
public class MockConfigs {
	@Value("${mock.config.origin-api}")
	private String originUrl;
	
	@Value("${mock.config.origin-load}")
	private boolean originLoad;
	
	@Value("${mock.config.origin-save}")
	private boolean originSave;

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public boolean isOriginLoad() {
		return originLoad;
	}

	public void setOriginLoad(boolean originLoad) {
		this.originLoad = originLoad;
	}

	public boolean isOriginSave() {
		return originSave;
	}

	public void setOriginSave(boolean originSave) {
		this.originSave = originSave;
	}

}

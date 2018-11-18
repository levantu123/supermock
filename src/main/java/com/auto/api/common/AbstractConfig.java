package com.auto.api.common;

public abstract class AbstractConfig {
	abstract public String getPrefix();
	
	abstract public Object getFromRealApi(String originUrl, String link, Object body);
}

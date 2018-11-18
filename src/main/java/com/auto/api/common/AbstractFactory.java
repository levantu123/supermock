package com.auto.api.common;

public abstract class AbstractFactory<O> {
	public abstract O initObj(String id, Object body, String link);
}

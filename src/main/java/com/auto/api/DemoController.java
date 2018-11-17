package com.auto.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v2")
public class DemoController {
	@GetMapping(value = "/hello")
	public Object get() {
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "hello get");
		return map;
	}
	
	@PostMapping(value = "/hello")
	public Object post(@RequestBody Object body) {
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "hello post");
		map.put("obj", body);
		return map;
	}
}

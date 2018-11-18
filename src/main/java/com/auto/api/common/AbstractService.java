package com.auto.api.common;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.auto.api.app.MockConfigs;
import com.auto.api.lib.TextAnalyzer;
import com.auto.api.model.Request;

public abstract class AbstractService<M extends AbstractConfig, O extends Request, A extends AbstractRepository<O>> {
	@Autowired
	A repository;

	@Autowired
	TextAnalyzer textAnalyzer;

	@Autowired
	private MockConfigs cockConfigs;

	@Autowired
	M serviceConfig;

	@Autowired
	AbstractFactory<O> factory;

	public void setServiceConfig(M serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

	public Object handleGet(String path, Map<String, String[]> para, Object body) {

		String url = textAnalyzer.buildUrl(path, para);
		String link = textAnalyzer.removePrefixUrl(url, "v1");
		String id = textAnalyzer.buildId(link);

		Optional<O> request = repository.findById(id);
		if (cockConfigs.isOriginLoad() && cockConfigs.isOriginSave() && !request.isPresent()) {
			Object map = serviceConfig.getFromRealApi(cockConfigs.getOriginUrl(), link, body);
			O o = factory.initObj(id, map, link);
			repository.save(o);
			return map;
		}

		if (cockConfigs.isOriginLoad() && cockConfigs.isOriginSave() && request.isPresent()) {
			Object map = serviceConfig.getFromRealApi(cockConfigs.getOriginUrl(), link, body);
			O o = factory.initObj(id, map, link);
			repository.save(o);
			return map;
		}

		if (cockConfigs.isOriginLoad()) {
			return new RestTemplate().getForEntity(cockConfigs.getOriginUrl() + link, Object.class).getBody();
		}

		if (request.isPresent()) {
			return request.get().getBodyGet();
		}

		return null;
	}

	public Object handlePost(String path, Map<String, String[]> para, Object body) {
		String url = textAnalyzer.buildUrl(path, para);
		String link = textAnalyzer.removePrefixUrl(url, serviceConfig.getPrefix());
		String id = textAnalyzer.buildId(link);

		if (repository.findById(id).isPresent()) {
			O o = repository.findById(id).get();
			if (o.getBodyGet() != null) {
				return o;
			}
			o.setBodyGet(body);
			return repository.save(o);
		}

		O o = factory.initObj(id, body, link);
		return repository.save(o);
	}

	public Object handlePut(String path, Map<String, String[]> para, Object body) {
		String url = textAnalyzer.buildUrl(path, para);
		String link = textAnalyzer.removePrefixUrl(url, serviceConfig.getPrefix());
		String id = textAnalyzer.buildId(link);

		if (repository.findById(id).isPresent()) {
			O o = repository.findById(id).get();
			o.setBodyGet(body);
			return repository.save(o);
		}
		return null;
	}
}

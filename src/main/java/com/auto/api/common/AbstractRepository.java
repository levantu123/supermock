package com.auto.api.common;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.auto.api.model.Request;

public interface AbstractRepository<O extends Request> extends MongoRepository<O, String>{
}
package com.auto.api.common;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbstractRepository<O> extends MongoRepository<O, String>{
}
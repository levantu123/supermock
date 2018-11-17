package com.auto.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.auto.api.model.Request;

public interface RequestRepository extends MongoRepository<Request, String>{
}

package com.spring.boot.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.spring.boot.reactive.model.User;

import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
	@Query("select * from users where age >= $1")
	Flux<User> findByAge(int age);
}

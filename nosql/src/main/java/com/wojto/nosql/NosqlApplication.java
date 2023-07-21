package com.wojto.nosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@SpringBootApplication
@EnableCouchbaseRepositories(basePackages = { "com.wojto.nosql.repo" })
public class NosqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(NosqlApplication.class, args);
	}

}

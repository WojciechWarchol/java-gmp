package com.wojto.nosql.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.wojto.nosql.repo"})
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    public static final String NODE_LIST = "couchbase";
    public static final String BUCKET_NAME = "users";
    public static final String BUCKET_USERNAME = "admin";
    public static final String BUCKET_PASSWORD = "pass";

    @Override
    public String getConnectionString() {
        return NODE_LIST;
    }

    @Override
    public String getUserName() {
        return BUCKET_USERNAME;
    }

    @Override
    public String getPassword() {
        return BUCKET_PASSWORD;
    }

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }
}

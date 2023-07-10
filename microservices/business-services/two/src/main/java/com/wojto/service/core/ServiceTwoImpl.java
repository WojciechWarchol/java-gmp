package com.wojto.service.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class ServiceTwoImpl implements ServiceTwo {

    private static final String ZUUL_URL = "http://apigateway:8762/";
    private static final String API_PATH = "/api";
    private static final String PROPERTIES_PATH = "/prop";
    private static final String HELLO_MESSAGE_PARAM_PATH = "/property-hello";
    private static final String TWO_MESSAGE_PARAM_PATH = "/property-two";

    private String serviceMessage;

    @PostConstruct
    public void init() {
        RestTemplate restTemplate = new RestTemplate();

        String propertiesUri = ZUUL_URL + API_PATH + PROPERTIES_PATH;
        String helloMessageUri = propertiesUri + HELLO_MESSAGE_PARAM_PATH;
        String twoMessageUri = propertiesUri + TWO_MESSAGE_PARAM_PATH;

        String helloMessage = restTemplate.exchange(helloMessageUri, HttpMethod.GET, null, String.class).getBody();
        String twoInMessage = restTemplate.exchange(twoMessageUri, HttpMethod.GET, null, String.class).getBody();

        serviceMessage = helloMessage + " " + twoInMessage;
    }

    @Override
    public String getServiceMessage() {
        return serviceMessage;
    }
}

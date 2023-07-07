package com.wojto.service.rest;

import com.wojto.service.core.ServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceTwoRestController {

    private ServiceTwo serviceTwo;

    @Autowired
    public ServiceTwoRestController(ServiceTwo serviceTwo) {
        this.serviceTwo = serviceTwo;
    }

    @GetMapping("/hello")
    public String getHelloFromServiceOne() {
        return serviceTwo.getServiceMessage();
    }

}

package com.wojto.service.rest;

import com.wojto.service.core.ServiceOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceOneRestController {

    private ServiceOne serviceOne;

    @Autowired
    public ServiceOneRestController(ServiceOne serviceOne) {
        this.serviceOne = serviceOne;
    }

    @GetMapping("/hello")
    public String getHelloFromServiceOne() {
        return serviceOne.getServiceMessage();
    }

}

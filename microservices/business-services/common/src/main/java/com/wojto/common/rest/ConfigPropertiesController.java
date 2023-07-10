package com.wojto.common.rest;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/properties")
public class ConfigPropertiesController {

    private DynamicStringProperty propertyHelloWithDynamic
            = DynamicPropertyFactory.getInstance()
            .getStringProperty("com.wojto.properties.hello", "not found!");

    private DynamicStringProperty propertyOneWithDynamic
            = DynamicPropertyFactory.getInstance()
            .getStringProperty("com.wojto.properties.one", "not found!");

    private DynamicStringProperty propertyTwoWithDynamic
            = DynamicPropertyFactory.getInstance()
            .getStringProperty("com.wojto.properties.two", "not found!");

    @GetMapping("/property-hello")
    public String getPropertyHelloValue() {
        return propertyHelloWithDynamic.get();
    }

    @GetMapping("/property-one")
    public String getPropertyOneValue() {
        return propertyOneWithDynamic.get();
    }

    @GetMapping("/property-two")
    public String getPropertyTwoValue() {
        return propertyTwoWithDynamic.get();
    }

}

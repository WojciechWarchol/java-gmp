package com.wojto.kafka.client.controller;

import com.wojto.kafka.client.service.OrderService;
import com.wojto.kafka.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order sendOrder(@RequestParam("order") Order order) {
        log.debug("Order Controller sending Order: " + order.toString());
        return orderService.processOrder(order);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Order getOrder(@PathVariable("id") long orderId) {
        log.debug("Order controller getting Order with ID: " + orderId );
        return orderService.getOrder(orderId);
    }
}

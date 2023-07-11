package com.wojto.kafka.client.repository;

import com.wojto.kafka.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderId(long orderId);
    List<Order> findByUserId(long userId);
    List<Order> findAll();

    Order save(Order order);
}

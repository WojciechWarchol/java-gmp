package com.wojto.kafka.client.repository;

import com.wojto.kafka.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(long orderId);
    List<Order> findByUserId(long userId);
    List<Order> findAll();

}

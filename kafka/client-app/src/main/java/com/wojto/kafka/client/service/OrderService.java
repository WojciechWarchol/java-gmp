package com.wojto.kafka.client.service;

import com.wojto.kafka.client.repository.OrderRepository;
import com.wojto.kafka.model.Notification;
import com.wojto.kafka.model.Order;
import com.wojto.kafka.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String topicName;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(
            final KafkaTemplate<String, Order> kafkaTemplate,
            @Value("${tpd.order-topic-name}") final String topicName,
            final OrderRepository orderRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
        this.orderRepository = orderRepository;
    }

    public Order processOrder(Order order) {
        order.setOrderStatus(OrderStatus.IN_PREPARATION);
//        order.getOrderContents().forEach(p -> p.setOrder(order));
        Order newOrder = orderRepository.save(order);
        if (newOrder == null) {
            new RuntimeException("Failed to save the Order to the database.");
        }
        ListenableFuture<SendResult<String, Order>> future = kafkaTemplate.send(topicName, newOrder);

        future.addCallback(
                result -> {
                    // Kafka sending succeeded
                    log.info("Order sent to Kafka successfully: " + result.getRecordMetadata());
                },
                ex -> {
                    // Kafka sending failed
                    log.error("Failed to send order to Kafka: " + ex.getMessage());
                }
        );

        return newOrder;
    }

    @KafkaListener(topics = "notification", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "3")
    public void listenForOrderUpdates(ConsumerRecord<String, Order> cr, @Payload Notification payload) {
        log.info("Client app received status Notification of Order with ID: " + payload.getOrderId());
        log.debug("Received Notification: " + payload.toString());

        Order updateOrder = orderRepository.findByOrderId(payload.getOrderId())
                .orElseThrow(() -> new RuntimeException("Didn't find Order to update status!"));
        log.info("Client app found Order with ID: " + payload.getOrderId() + ". Saving updated status to DB.");
        updateOrder.setOrderStatus(payload.getOrderStatus());

        orderRepository.save(updateOrder);
    }

    public Order getOrder(long orderId) {
        Optional<Order> newOrder = orderRepository.findByOrderId(orderId);
        return newOrder.orElseThrow(() -> new RuntimeException("Failed to find order with ID: " + orderId));
    }


}

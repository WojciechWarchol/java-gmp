package com.wojto.kafka.client.service;

import com.wojto.kafka.client.repository.OrderRepository;
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
            containerFactory = "kafkaListenerContainerFactory")
    public void listenForOrderUpdates(ConsumerRecord<String, Order> cr,
                                      @Payload Order payload) {
        log.info("Client app received Order with ID: " + payload.getOrderId() + ". Saving it to DB.");
        log.debug("Received Order: " + payload.toString());
        orderRepository.save(payload);
//        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
//                typeIdHeader(cr.headers()), payload, cr.toString());
//        latch.countDown();
    }

    public Order getOrder(long orderId) {
        Optional<Order> newOrder = orderRepository.findByOrderId(orderId);
        return newOrder.orElseThrow(() -> new RuntimeException("Failed to find order with ID: " + orderId));
    }


}

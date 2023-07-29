package com.wojto.kafka.courier.service;

import com.wojto.kafka.model.Notification;
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

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

@Slf4j
@Service
public class DeliveryProcessor {

    private final int THREADS_NUMBER = Runtime.getRuntime().availableProcessors() / 2;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);

    private final KafkaTemplate<String, Notification> kafkaTemplate;
    private final String topicName;

    @Autowired
    public DeliveryProcessor(final KafkaTemplate<String, Notification> kafkaTemplate,
                             @Value("${tpd.notification-topic-name}") final String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @PreDestroy
    public void shutdownOrderProcessor() {
        executor.shutdown();
    }

    @KafkaListener(topics = "notification", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "3")
    public void listenForOrderUpdates(ConsumerRecord<String, Notification> cr, @Payload Notification payload) {
        if (payload.getOrderStatus() != OrderStatus.IN_DELIVERY) return;

        log.info("Courier app received Order with ID: " + payload.getOrderId() + ". Delivering Pizzas");
        log.debug("Received Order: " + payload.toString());

        payload.setOrderStatus(deliverOrder(payload));
        broadcastOrderStatusToKafka(payload);
    }

    public OrderStatus deliverOrder(Notification notification) {

        Future<Notification> orderInDelivery = executor.submit(() -> {
            try {
                // Simulate time it takes to deliver Pizza
                int sleepTime = 5000 + (int) (Math.random() * 10000);
                Thread.sleep(sleepTime);

                log.info("Delivered order number: " + notification.getOrderId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return notification;
        });


        try {
            orderInDelivery.get(30, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            log.error("Pizza probably lost in transit!");
            throw new RuntimeException("Pizza lost during delivery!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return OrderStatus.DELIVERED;
    }

    private void broadcastOrderStatusToKafka(Notification orderStatus) {
        ListenableFuture<SendResult<String, Notification>> future = kafkaTemplate.send(topicName, orderStatus);

        future.addCallback(
                result -> {
                    // Kafka sending succeeded
                    log.info("OrderStatus sent to Kafka successfully: " + result.getRecordMetadata());
                },
                ex -> {
                    // Kafka sending failed
                    log.error("Failed to send OrderStatus to Kafka: " + ex.getMessage());
                }
        );
    }
}

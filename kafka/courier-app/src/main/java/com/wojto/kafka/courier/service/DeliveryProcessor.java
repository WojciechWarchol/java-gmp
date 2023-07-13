package com.wojto.kafka.courier.service;

import com.wojto.kafka.model.Notification;
import com.wojto.kafka.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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

    private final KafkaTemplate<String, OrderStatus> template;
    private final String topicName;

    public DeliveryProcessor(final KafkaTemplate<String, OrderStatus> template,
                             @Value("${tpd.topic-name}") final String topicName) {
        this.template = template;
        this.topicName = topicName;
    }

    @PreDestroy
    public void shutdownOrderProcessor() {
        executor.shutdown();
    }

    @KafkaListener(topics = "notification", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenForOrderUpdates(ConsumerRecord<String, OrderStatus> cr,
                                      @Payload Notification payload) {
        log.info("Courier app received Order with ID: " + payload.getOrderId() + ". Delivering Pizzas");
        log.debug("Received Order: " + payload.toString());

        OrderStatus orderStatus = deliverOrder(payload);
        broadcastOrderStatusToKafka(orderStatus);
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

    private void broadcastOrderStatusToKafka(OrderStatus orderStatus) {
        ListenableFuture<SendResult<String, OrderStatus>> future = template.send(topicName, orderStatus);

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

package com.wojto.kafka.palmetto.service;

import com.wojto.kafka.model.Order;
import com.wojto.kafka.model.OrderStatus;
import com.wojto.kafka.model.Pizza;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
public class OrderProcessor {

    private final int THREADS_NUMBER = Runtime.getRuntime().availableProcessors() / 2;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);

    private final KafkaTemplate<String, OrderStatus> template;
    private final String topicName;

    public OrderProcessor(final KafkaTemplate<String, OrderStatus> template,
                          @Value("${tpd.topic-name}") final String topicName) {
        this.template = template;
        this.topicName = topicName;
    }

    @PreDestroy
    public void shutdownOrderProcessor() {
        executor.shutdown();
    }

    @KafkaListener(topics = "order", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenForOrderUpdates(ConsumerRecord<String, Order> cr,
                                      @Payload Order payload) {
        log.info("Palmetto app received Order with ID: " + payload.getOrderId() + ". Cooking Pizzas");
        log.debug("Received Order: " + payload.toString());

        OrderStatus orderStatus = OrderStatus.IN_PREPARATION;
        broadcastOrderStatusToKafka(orderStatus);

        orderStatus = cookPizzas(payload.getOrderContents());
        broadcastOrderStatusToKafka(orderStatus);
    }

    public OrderStatus cookPizzas(List<Pizza> PizzaList) {

        List<Future<Pizza>> cookedPizzas = new ArrayList<>();
        for (Pizza pizza : PizzaList) {
            cookedPizzas.add(executor.submit(() -> {
                try {
                    // Simulate time it takes to cook Pizza
                    int sleepTime = 5000 + (int) (Math.random() * 10000);
                    Thread.sleep(sleepTime);

                    log.info("cooked pizza number: " + pizza.getNumber());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return pizza;
            }));
        }

        for (Future<Pizza> future : cookedPizzas) {
            try {
                future.get(30, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                log.error("Cooking Pizza takes too long!");
                throw new RuntimeException("Waiting too long for pizza");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return OrderStatus.IN_DELIVERY;
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

package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter @Setter
public class Order {

    private long orderId;
    private long userId;
    private List<Pizza> orderContents;
    private OrderStatus orderStatus;
    private Address deliveryAddress;

    public Order(long userId, List<Pizza> orderContents, Address deliveryAddress) {
        this.userId = userId;
        this.orderContents = orderContents;
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getOrderTotal() {
        BigDecimal orderTotal = BigDecimal.ZERO;
        orderTotal = orderContents.stream()
                .map(Pizza::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return orderTotal;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderContents=" +
                orderContents.stream().map(Pizza::toString).collect(Collectors.joining("\n")) +
                ", orderStatus=" + orderStatus +
                ", deliveryAddress=" + deliveryAddress +
                '}';
    }
}

package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class NormalOrder implements Order {

    private long orderNumber;
    private long userId;
    private List<Pizza> orderContents;
    private OrderStatus orderStatus;
    private Address deliveryAddress;

    public NormalOrder(long userId, List<Pizza> orderContents, Address deliveryAddress) {
        this.userId = userId;
        this.orderContents = orderContents;
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public BigDecimal getOrderTotal() {
        BigDecimal orderTotal = BigDecimal.ZERO;
        orderTotal = orderContents.stream()
                .map(Pizza::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return orderTotal;
    }
}

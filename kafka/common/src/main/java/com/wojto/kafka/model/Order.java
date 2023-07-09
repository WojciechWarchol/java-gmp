package com.wojto.kafka.model;

import java.math.BigDecimal;

public interface Order {

    public long getOrderNumber();
    public OrderStatus getOrderStatus();
    public BigDecimal getOrderTotal();
}

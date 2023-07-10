package com.wojto.kafka.model;

import java.math.BigDecimal;
import java.util.List;

public interface Order {

    public long getOrderNumber();
    public long getUserId();
    public List<Pizza> getOrderContents();
    public OrderStatus getOrderStatus();
    public BigDecimal getOrderTotal();
    public Address getDeliveryAddress();

}

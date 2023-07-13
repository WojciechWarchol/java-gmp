package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter
@ToString
public class Notification {

    private long orderId;
    private OrderStatus orderStatus;

}

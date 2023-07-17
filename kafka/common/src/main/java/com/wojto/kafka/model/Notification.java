package com.wojto.kafka.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Notification {

    private long orderId;
    private OrderStatus orderStatus;

}

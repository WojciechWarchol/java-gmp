package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @Column
    private long userId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pizza> orderContents;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Embedded
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

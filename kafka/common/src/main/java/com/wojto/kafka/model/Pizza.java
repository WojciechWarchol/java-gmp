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
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Ingredient> ingredients;
    @Enumerated(EnumType.STRING)
    private PizzaSize size;

    public Pizza(Order order, List<Ingredient> ingredients, PizzaSize size) {
        this.order = order;
        this.ingredients = ingredients;
        this.size = size;
    }

    public BigDecimal getPrice() {
        BigDecimal priceModifier = size.getPriceModifier();
        return size.getBasePrice()
                .add(ingredients.stream()
                        .map(i -> i.getPrice().add(priceModifier))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "number=" + id +
                ", ingredients=" +
                ingredients.stream()
                        .map(Ingredient::getName)
                        .collect(Collectors.joining(", "))  +
                ", size=" + size.getSize() +
                ", price=" + getPrice() +
                "}";
    }
}

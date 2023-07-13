package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter @Setter
public class Pizza {

    private int number;
    private List<Ingredient> ingredients;
    private PizzaSize size;

    public Pizza(List<Ingredient> ingredients, PizzaSize size) {
        this.ingredients = ingredients;
        this.size = size;
    }

    public BigDecimal getPrice() {
        BigDecimal priceModifier = size.getPriceModifier();
        BigDecimal price = size.getBasePrice()
                .add(ingredients.stream()
                        .map(i -> i.getPrice().add(priceModifier))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        return price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "number=" + number +
                ", ingredients=" +
                ingredients.stream()
                        .map(Ingredient::getName)
                        .collect(Collectors.joining(", "))  +
                ", size=" + size.getSize() +
                ", price=" + getPrice() +
                "}";
    }
}

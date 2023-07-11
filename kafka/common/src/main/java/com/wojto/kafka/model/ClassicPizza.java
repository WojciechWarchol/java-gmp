package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class ClassicPizza implements com.wojto.kafka.model.Pizza {

    private int number;
    private List<Ingredient> ingredients;
    private PizzaSize size;

    public ClassicPizza(List<Ingredient> ingredients, PizzaSize size) {
        this.ingredients = ingredients;
        this.size = size;
    }

    @Override
    public BigDecimal getPrice() {
        BigDecimal priceModifier = size.getPriceModifier();
        BigDecimal price = size.getBasePrice()
                .add(ingredients.stream()
                        .map(i -> i.getPrice().add(priceModifier))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        return price;
    }
}

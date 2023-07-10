package com.wojto.kafka.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassicPizzaTest {

    @Test
    void getPrice() {
        List<Ingredient> ingredientList = List.of(
                Ingredient.TOMATO_SAUCE,
                Ingredient.MOZZARELLA,
                Ingredient.SALAMI
        );
        Pizza testPizza = new ClassicPizza(1, ingredientList, PizzaSize.M);

        assertEquals(BigDecimal.valueOf(34), testPizza.getPrice());
    }
}
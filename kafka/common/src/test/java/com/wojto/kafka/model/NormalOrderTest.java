package com.wojto.kafka.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NormalOrderTest {

    @Test
    void getOrderTotal() {
        List<Ingredient> ingredientList1 = List.of(
                Ingredient.TOMATO_SAUCE,
                Ingredient.MOZZARELLA,
                Ingredient.SALAMI
        );
        List<Ingredient> ingredientList2 = List.of(
                Ingredient.GARLIC_SAUCE,
                Ingredient.RICOTTA,
                Ingredient.BACON,
                Ingredient.ONIONS
        );
        Pizza testPizza1 = new ClassicPizza(1, ingredientList1, PizzaSize.M);
        Pizza testPizza2 = new ClassicPizza(2, ingredientList2, PizzaSize.L);
        List<Pizza> orderContents = List.of(testPizza1, testPizza2);

        NormalOrder testOrder = new NormalOrder(1, orderContents, new Address());

        assertEquals(BigDecimal.valueOf(82), testOrder.getOrderTotal());
    }
}
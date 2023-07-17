package com.wojto.kafka.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

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
//        Pizza testPizza1 = new Pizza(1, new Order(), ingredientList1, PizzaSize.M);
//        Pizza testPizza2 = new Pizza(2, new Order(), ingredientList2, PizzaSize.L);
        Pizza testPizza1 = new Pizza(1, ingredientList1, PizzaSize.M);
        Pizza testPizza2 = new Pizza(2, ingredientList2, PizzaSize.L);
        List<Pizza> orderContents = List.of(testPizza1, testPizza2);

        Order testOrder = new Order(1, orderContents, new Address());

        assertEquals(BigDecimal.valueOf(82), testOrder.getOrderTotal());
    }
}
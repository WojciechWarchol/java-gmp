package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum Ingredient {

    TOMATO_SAUCE(IngredientType.SAUCE, "Tomato Sauce", BigDecimal.valueOf(5)),
    GARLIC_SAUCE(IngredientType.SAUCE, "Garlic Sauce", BigDecimal.valueOf(5)),
    MOZZARELLA(IngredientType.CHEESE, "Mozzarella", BigDecimal.valueOf(6)),
    RICOTTA(IngredientType.CHEESE, "Ricotta", new BigDecimal(8)),
    GORGONZOLA(IngredientType.CHEESE, "Gorgonzola", BigDecimal.valueOf(8)),
    PARMESAN(IngredientType.CHEESE, "Parmesan", BigDecimal.valueOf(6)),
    HAM(IngredientType.MEAT, "Ham", BigDecimal.valueOf(8)),
    BACON(IngredientType.MEAT, "Bacon", BigDecimal.valueOf(9)),
    SALAMI(IngredientType.MEAT, "Salami", BigDecimal.valueOf(10)),
    CHICKEN(IngredientType.MEAT, "Chicken", BigDecimal.valueOf(11)),
    BEEF(IngredientType.MEAT, "Beef", BigDecimal.valueOf(12)),
    ONIONS(IngredientType.VEGGIE, "Onions", BigDecimal.valueOf(3)),
    TOMATOES(IngredientType.VEGGIE, "Tomatoes", BigDecimal.valueOf(3)),
    SPINACH(IngredientType.VEGGIE, "Spinach", BigDecimal.valueOf(2)),
    BELL_PEPPERS(IngredientType.VEGGIE, "Bell Peppers", BigDecimal.valueOf(4)),
    OLIVES(IngredientType.VEGGIE, "Olives", BigDecimal.valueOf(4)),
    PINEAPPLE(IngredientType.FRUIT, "Pineapple", BigDecimal.valueOf(5)),
    PEAR(IngredientType.FRUIT, "Pear", BigDecimal.valueOf(4)),
    MUSHROOM(IngredientType.OTHER, "Mushrooms", BigDecimal.valueOf(3)),
    BASIL(IngredientType.OTHER, "Basil", BigDecimal.valueOf(3)),
    WALNUTS(IngredientType.OTHER, "Walnuts", BigDecimal.valueOf(4));;

    private final IngredientType type;
    private final String name;
    private final BigDecimal price;

    private enum IngredientType {
        SAUCE, CHEESE, MEAT, VEGGIE, FRUIT, OTHER
    }

}

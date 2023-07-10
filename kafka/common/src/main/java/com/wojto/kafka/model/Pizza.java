package com.wojto.kafka.model;

import java.math.BigDecimal;
import java.util.List;

public interface Pizza {

    public int getNumber();
    public List<Ingredient> getIngredients();
    public PizzaSize getSize();
    public BigDecimal getPrice();

}

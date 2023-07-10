package com.wojto.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum PizzaSize {
    S("S", BigDecimal.valueOf(5), BigDecimal.valueOf(0)),
    M("M", BigDecimal.valueOf(10), BigDecimal.valueOf(1)),
    L("L", BigDecimal.valueOf(15), BigDecimal.valueOf(2)),
    XL("XL", BigDecimal.valueOf(20), BigDecimal.valueOf(3))
    ;

    private final String size;
    private final BigDecimal basePrice;
    private final BigDecimal priceModifier;

}

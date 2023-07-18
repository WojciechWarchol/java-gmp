package com.wojto.recursivetask;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTaskTest {

    @org.junit.jupiter.api.Test
    void compute() {
        assertEquals(1134903170L, new ForkJoinPool().invoke(new FibonacciTask(45)).longValue());
    }
}
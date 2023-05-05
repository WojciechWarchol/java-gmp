package com.wojto;

import com.wojto.task2.IntegerGenerator;
import com.wojto.task2.MergeSortTask;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        List<Integer> integers = IntegerGenerator.generateIntegers(1000000, 1000000,true);
        System.out.println(integers.size());

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new MergeSortTask(integers, 0, integers.size() - 1));
        pool.shutdown();
        System.out.println(integers);
    }
}
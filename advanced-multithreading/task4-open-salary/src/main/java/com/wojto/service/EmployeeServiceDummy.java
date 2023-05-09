package com.wojto.service;

import com.wojto.model.Employee;
import com.wojto.model.Salary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class EmployeeServiceDummy {

    private final Random random = new Random();

    public CompletableFuture<List<Employee>> hiredEmplyees() {
        return CompletableFuture.supplyAsync(() -> {
            // simulate some delay
            try {
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // generate a list of employees
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(1L, "John", "Doe", "Sales"));
            employees.add(new Employee(2L, "Jane", "Doe", "Marketing"));
            employees.add(new Employee(3L, "Bob", "Smith", "IT"));
            employees.add(new Employee(4L, "Alice", "Brown", "HR"));
            employees.add(new Employee(5L, "John", "Smith", "Marketing"));
            employees.add(new Employee(6L, "Jane", "Doe", "Sales"));
            employees.add(new Employee(7L, "Michael", "Johnson", "HR"));
            employees.add(new Employee(8L, "Emily", "Wilson", "IT"));
            employees.add(new Employee(9L, "Robert", "Brown", "Finance"));
            employees.add(new Employee(10L, "Amanda", "Davis", "Operations"));

            return employees;
        });
    }

    public CompletionStage<Salary> getSalary(long hiredEmployeeId) {
        CompletableFuture<Salary> future = new CompletableFuture<>();

        // Simulate some delay for the response
        int delayMs = random.nextInt(500) + 500;
        new Thread(() -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
                return;
            }

            // Create a Salary object with a random compensation value between 50000 and 100000
            BigDecimal compensation = BigDecimal.valueOf(random.nextInt(50000) + 50000);
            Salary salary = new Salary(hiredEmployeeId, compensation);

            future.complete(salary);
        }).start();

        return future;
    }

}

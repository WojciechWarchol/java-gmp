package com.wojto;

import com.wojto.model.Employee;
import com.wojto.service.EmployeeServiceDummy;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EmployeeServiceDummy employeeService = new EmployeeServiceDummy();

        CompletableFuture<List<Employee>> employees = employeeService.hiredEmplyees();

        CompletableFuture<Void> salaries = employees.thenCompose(employeeList ->
                CompletableFuture.allOf(employeeList.stream()
                        .map(employee -> employeeService.getSalary(employee.getId())
                                .thenAccept(employee::setSalary))
                        .toArray(CompletableFuture[]::new)));

        salaries.join();

        employees.thenAccept(employeeList -> {
            for (Employee employee : employeeList) {
                System.out.println(employee);
            }
        }).join();
        
    }
}
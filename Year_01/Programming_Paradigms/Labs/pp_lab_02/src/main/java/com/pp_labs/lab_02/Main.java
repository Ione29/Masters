package com.pp_labs.lab_02;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> employees = populateEmployeeList();

        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            employeeMap.put(employee.getName().toLowerCase(), employee); // Use lowercase for case-insensitive search
        }

        // Use Scanner to get input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the employee: ");
        String name = scanner.nextLine();

        // Find and display the employee
        String result = findEmployeeByName(employeeMap, name);
        System.out.println(result);

        scanner.close();
    }

    public static ArrayList<Employee> populateEmployeeList() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", LocalDate.of(2020, 1, 1), LocalDate.of(1990, 6, 15), "HR Manager"));
        employees.add(new Employee(2, "Bob", LocalDate.of(2019, 4, 10), LocalDate.of(1985, 11, 20), "Software Engineer"));
        employees.add(new Employee(3, "Charlie", LocalDate.of(2021, 7, 5), LocalDate.of(1992, 9, 25), "Data Analyst"));
        employees.add(new Employee(4, "Diana", LocalDate.of(2018, 3, 20), LocalDate.of(1988, 4, 10), "Marketing Specialist"));
        employees.add(new Employee(5, "Eve", LocalDate.of(2022, 12, 15), LocalDate.of(1995, 2, 5), "Sales Representative"));
        employees.add(new Employee(6, "Frank", LocalDate.of(2017, 8, 30), LocalDate.of(1983, 5, 18), "IT Support"));
        employees.add(new Employee(7, "Grace", LocalDate.of(2020, 10, 12), LocalDate.of(1991, 7, 22), "HR Assistant"));
        employees.add(new Employee(8, "Hank", LocalDate.of(2016, 6, 25), LocalDate.of(1980, 12, 30), "Finance Manager"));
        employees.add(new Employee(9, "Ivy", LocalDate.of(2023, 1, 10), LocalDate.of(1998, 3, 14), "Intern"));
        employees.add(new Employee(10, "Jack", LocalDate.of(2015, 9, 18), LocalDate.of(1987, 8, 9), "Project Manager"));
        return employees;
    }

    public static String findEmployeeByName(Map<String, Employee> employeeMap, String name) {
        Employee employee = employeeMap.get(name.toLowerCase());
        if (employee != null) {
            return "Employee found: " + employee;
        } else {
            return "Employee not found.";
        }
    }
}
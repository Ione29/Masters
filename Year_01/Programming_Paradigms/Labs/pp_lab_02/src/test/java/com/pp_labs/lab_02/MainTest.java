package com.pp_labs.lab_02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void testPopulateEmployeeList() {
        ArrayList<Employee> employees = Main.populateEmployeeList();

        assertEquals(10, employees.size(), "The list should contain 10 employees.");

        assertEquals("Alice", employees.get(0).getName(), "The first employee should be Alice.");
        assertEquals("Jack", employees.get(9).getName(), "The last employee should be Jack.");
    }

    @Test
    void testFindEmployeeByName_EmployeeExists() {
        ArrayList<Employee> employees = Main.populateEmployeeList();
        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            employeeMap.put(employee.getName().toLowerCase(), employee);
        }

        String result = Main.findEmployeeByName(employeeMap, "Alice");

        assertTrue(result.contains("Employee found"), "The result should indicate the employee was found.");
        assertTrue(result.contains("Alice"), "The result should contain the employee's name.");
    }

    @Test
    void testFindEmployeeByName_EmployeeDoesNotExist() {

        ArrayList<Employee> employees = Main.populateEmployeeList();
        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            employeeMap.put(employee.getName().toLowerCase(), employee);
        }

        String result = Main.findEmployeeByName(employeeMap, "Zara");

        assertEquals("Employee not found.", result, "The result should indicate the employee was not found.");
    }

    @Test
    void testFindEmployeeByName_CaseInsensitiveSearch() {
        ArrayList<Employee> employees = Main.populateEmployeeList();
        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            employeeMap.put(employee.getName().toLowerCase(), employee);
        }

        String result = Main.findEmployeeByName(employeeMap, "alice");

        assertTrue(result.contains("Employee found"), "The result should indicate the employee was found.");
        assertTrue(result.contains("Alice"), "The result should contain the employee's name.");
    }
}
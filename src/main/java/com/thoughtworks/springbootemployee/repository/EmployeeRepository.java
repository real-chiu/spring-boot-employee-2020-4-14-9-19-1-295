package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(0, "Xiaoming", 20, "Male", 5000));
        employees.add(new Employee(1, "Xiaohong", 19, "Male", 5000));
        employees.add(new Employee(2, "Xiaozhi", 15, "Male", 5000));
        employees.add(new Employee(3, "Xiaoxia", 16, "Female", 5000));
    }

    public List<Employee> findAllEmployee(String gender) {
        if (gender == null) {
            return employees;
        }
        return employees.stream().filter(employee -> employee.getGender().toLowerCase().equals(gender.toLowerCase())).collect(Collectors.toList());
    }

    public Employee findEmployeeById(int employeeId) {
        return employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
    }
}

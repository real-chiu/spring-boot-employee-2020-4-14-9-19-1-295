package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Employee> companies = new ArrayList<>();

    public CompanyController() {
        List<Employee> employees = new ArrayList<>();

        Company company = new Company(0, "OOCL", 0, new ArrayList<>());
        company.addEmployee(new Employee(0, "Xiaoming", 20, "Male", 5000));
        company.addEmployee(new Employee(1, "Xiaohong", 19, "Male", 7000));
        company.addEmployee(new Employee(2, "Xiaozhi", 15, "Male", 9000));
        company.addEmployee(new Employee(3, "Xiaoxia", 16, "Female", 10000));
    }
}

package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;


public class Company {
    private int id;
    private String companyName;
    private int employeesNumber;
    private List<Employee> employees;

    public Company() {
    }

    public Company(int id, String companyName, int employeesNumber, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        //
        employeesNumber = employees.size();
    }


    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


}

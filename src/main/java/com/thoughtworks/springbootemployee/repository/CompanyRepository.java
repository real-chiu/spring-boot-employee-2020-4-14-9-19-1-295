package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        Company company = new Company(0, "OOCL", 0, new ArrayList<>());
        company.addEmployee(new Employee(0, "Xiaoming", 20, "Male", 5000));
        company.addEmployee(new Employee(1, "Xiaohong", 19, "Male", 7000));
        company.addEmployee(new Employee(2, "Xiaozhi", 15, "Male", 9000));
        company.addEmployee(new Employee(3, "Xiaoxia", 16, "Female", 10000));

        Company companyTwo = new Company(1, "CargoSmart", 0, new ArrayList<>());
        companyTwo.addEmployee(new Employee(4, "A", 20, "Male", 5000));
        companyTwo.addEmployee(new Employee(5, "B", 19, "Male", 7000));
        companyTwo.addEmployee(new Employee(6, "C", 15, "Male", 9000));
        companyTwo.addEmployee(new Employee(7, "D", 16, "Female", 10000));

        companies.add(company);
        companies.add(companyTwo);
    }

    public List<Company> findAllCompany() {
        return companies;
    }
}

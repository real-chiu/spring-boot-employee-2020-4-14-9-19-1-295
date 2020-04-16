package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Company findCompanyById(int companyId) {
        return companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
    }

    public Company addNewCompany(Company companyToBeAdded) {
        companies.add(companyToBeAdded);
        return companyToBeAdded;
    }

    public void deleteEmployee(int companyId) {
        companies = companies.stream().filter(company -> company.getId() != companyId).collect(Collectors.toList());
    }

    public void updateCompany(Company companyWithChanges) {
        companies = companies.stream().map(company -> {
            if(company.getId() == companyWithChanges.getId()) {
                return companyWithChanges;
            }
            return company;
        }).collect(Collectors.toList());
    }
}

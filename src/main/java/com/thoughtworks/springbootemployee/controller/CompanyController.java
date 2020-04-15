package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new ArrayList<>();

    public CompanyController() {
        List<Employee> employees = new ArrayList<>();

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

    public List<Company> pagingCompanyList(List<Company> companies, Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            return companies;
        };
        Integer leftBound = (page-1) * pageSize;
        Integer rightBound = (page-1) * pageSize + pageSize;
        leftBound = leftBound > companies.size() - 1 ? 0 : leftBound;
        rightBound = rightBound > companies.size() - 1 ? companies.size() : rightBound;
        return companies.subList(leftBound,  rightBound);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Company>> getAllCompanies(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(pagingCompanyList(companies, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> getCompanyWithId(@PathVariable int companyId) {
        Company specificCompany =  companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
        if (specificCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(specificCompany, HttpStatus.OK);
    }
}
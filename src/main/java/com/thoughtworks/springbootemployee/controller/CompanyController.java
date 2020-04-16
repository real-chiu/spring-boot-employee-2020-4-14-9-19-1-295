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

    @GetMapping("/{companyId}/employees")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeesSpecificCompany(@PathVariable int companyId) {
        Company specificCompany =  companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
        if (specificCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<Employee> employeesOfCompany = specificCompany.getEmployees();
        return new ResponseEntity<>(employeesOfCompany, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> createNewCompany(@RequestBody Company companyToBeAdded) {
        Company newAddedCompany = companies.stream().filter(company -> companyToBeAdded.getId() == company.getId()).findFirst().orElse(null);
        if (newAddedCompany != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        companies.add(companyToBeAdded);
        return new ResponseEntity<>(companyToBeAdded, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> deleteCompanyWithId(@PathVariable int companyId) {
        Company deletedCompany = companies.stream().filter(company -> companyId == company.getId()).findFirst().orElse(null);
        if (deletedCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        companies = companies.stream().filter(company -> companyId != company.getId()).collect(Collectors.toList());
        return new ResponseEntity<>(deletedCompany, HttpStatus.OK);
    }

    @PutMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Company> updateEmployee(@PathVariable int companyId, @RequestBody Company companyWithChanges) {
        Company companyToBeUpdated = companies.stream().filter(company -> companyId == company.getId()).findFirst().orElse(null);
        if (companyToBeUpdated == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        companies = companies.stream().map(company -> {
            if(company.getId() == companyId) {
                return companyWithChanges;
            }
            return company;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(companyWithChanges, HttpStatus.OK);
    }
}

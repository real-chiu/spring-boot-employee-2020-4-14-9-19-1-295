package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Company>> getAllCompanies(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer pageSize) {
        List<Company> allCompanies = companyService.getAllCompany(page, pageSize);
        return new ResponseEntity<>(allCompanies, HttpStatus.OK);
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

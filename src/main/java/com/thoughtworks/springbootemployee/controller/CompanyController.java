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
        Company specificCompany =  companyService.getCompanyById(companyId);
        if (specificCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(specificCompany, HttpStatus.OK);
    }

    @GetMapping("/{companyId}/employees")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeesSpecificCompany(@PathVariable int companyId) {
        List<Employee> employeesOfCompany =  companyService.getEmployeeOfCompanyByCompanyId(companyId);
        if (employeesOfCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeesOfCompany, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> addNewCompany(@RequestBody Company companyToBeAdded) {
        Company newAddedCompany = companyService.addNewCompany(companyToBeAdded);
        if (newAddedCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(companyToBeAdded, HttpStatus.CREATED);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> deleteCompanyWithId(@PathVariable int companyId) {
        Company deletedCompany = companyService.deleteCompany(companyId);
        if (deletedCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
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

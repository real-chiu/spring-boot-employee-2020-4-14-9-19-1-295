package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompany(Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            Pageable pageable = PageRequest.of(page, pageSize);
            return companyRepository.findAll(pageable).getContent();
        }
        return companyRepository.findAll();
    }

    public Company getCompanyById(int companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeeOfCompanyByCompanyId(int companyId) {
        Company specificCompany = getCompanyById(companyId);
        if (specificCompany == null) {
            return null;
        }
        return specificCompany.getEmployees();
    }

    public Company addNewCompany(Company companyToBeAdded) {
        return companyRepository.save(companyToBeAdded);
    }

    public Company deleteCompany(int companyId) {
        Company companyToBeDeleted = companyRepository.findById(companyId).orElse(null);
        if (companyToBeDeleted == null) {
            return null;
        }
        companyRepository.deleteById(companyId);
        return companyToBeDeleted;
    }

    //
    public Company updateCompany(Integer companyId, String companyName, Integer employeesNumber, List<Employee> employees) {
        Company companyToBeUpdated = companyRepository.findById(companyId).orElse(null);
        if (companyToBeUpdated == null) {
            return null;
        }
        //
        Company companyWithChanges = getCompany(companyId, companyName, employeesNumber, employees, companyToBeUpdated);
        companyRepository.save(companyWithChanges);
        return companyWithChanges;
    }

    // put into Company
    private Company getCompany(Integer companyId, String companyName, Integer employeesNumber, List<Employee> employees, Company companyToBeUpdated) {
        return new Company(
                    companyId,
                    companyName  == null ? companyToBeUpdated.getCompanyName() : companyName,
                    employeesNumber == null ? companyToBeUpdated.getEmployeesNumber() : employeesNumber,
                    employees == null ? companyToBeUpdated.getEmployees() : employees
            );
    }
}

package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.commonUtils.Paging;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    private Paging paging = new Paging();

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompany(Integer page, Integer pageSize) {
        return paging.pagingCompanyList(companyRepository.findAllCompany(), page, pageSize);
    }

    public Company getCompanyById(int companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    public List<Employee> getEmployeeOfCompanyByCompanyId(int companyId) {
        Company specificCompany = getCompanyById(companyId);
        if (specificCompany == null) {
            return null;
        }
        return specificCompany.getEmployees();
    }

    public Company addNewCompany(Company companyToBeAdded) {
        boolean isCompanyWithIdAlreadyExist = companyRepository.findCompanyById(companyToBeAdded.getId()) != null;
        if (isCompanyWithIdAlreadyExist){
            return null;
        }
        return companyRepository.addNewCompany(companyToBeAdded);
    }
}

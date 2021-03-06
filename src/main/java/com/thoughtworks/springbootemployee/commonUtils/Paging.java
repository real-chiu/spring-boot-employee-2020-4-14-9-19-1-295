package com.thoughtworks.springbootemployee.commonUtils;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.List;

public class Paging {

    //combine
    public List<Employee> pagingEmployeeList(List<Employee> employees, Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            return employees;
        };
        Integer leftBound = (page-1) * pageSize;
        Integer rightBound = (page-1) * pageSize + pageSize;
        leftBound = leftBound > employees.size() - 1 ? 0 : leftBound;
        rightBound = rightBound > employees.size() - 1 ? employees.size() : rightBound;
        return employees.subList(leftBound,  rightBound);
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
}

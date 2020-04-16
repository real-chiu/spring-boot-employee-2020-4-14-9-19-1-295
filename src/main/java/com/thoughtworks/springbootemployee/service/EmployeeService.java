package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.commonUtils.Paging;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private Paging paging = new Paging();

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(String gender, Integer page, Integer pageSize) {
        return paging.pagingEmployeeList(employeeRepository.findAllEmployee(gender), page, pageSize);
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findEmployeeById(employeeId);
    }
}

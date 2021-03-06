package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(String gender, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            Pageable pageable = PageRequest.of(page, pageSize);
            return employeeRepository.findAllByGender(gender, pageable);
        }
        if (gender != null) {
            return employeeRepository.findAllByGender(gender);
        }
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public Employee addNewEmployee(Employee employeeTobeAdded) {
        return employeeRepository.save(employeeTobeAdded);
    }

    public Employee deleteEmployee(int employeeId) {
        Employee employeeToBeDeleted = employeeRepository.findById(employeeId).orElse(null);
        if (employeeToBeDeleted == null) {
            return null;
        }
        employeeRepository.deleteById(employeeId);
        return employeeToBeDeleted;
    }

    public Employee updateEmployee(Integer employeeId, Employee employeeDetailsTobeUpdated) {
        Employee employeeToBeUpdated = employeeRepository.findById(employeeId).orElse(null);
        if (employeeToBeUpdated == null) {
            return null;
        }
        employeeToBeUpdated.update(employeeDetailsTobeUpdated);
        employeeRepository.save(employeeToBeUpdated);
        return employeeToBeUpdated;
    }

}

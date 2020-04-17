package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.commonUtils.Paging;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = null;
        if (page != null && pageSize != null) {
            pageable = PageRequest.of(page, pageSize);
        }
        return paging.pagingEmployeeList(employeeRepository.findAllByGender(gender, pageable), page, pageSize);
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public Employee addNewEmployee(Employee employeeTobeAdded) {
        boolean isEmployeeWithIdAlreadyExist = employeeRepository.findById(employeeTobeAdded.getId()) != null;
        if (isEmployeeWithIdAlreadyExist){
            return null;
        }
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

    public Employee updateEmployee(Integer employeeId, Employee employeeTobeUpdated) {
        Employee employeeToBeUpdated = employeeRepository.findById(employeeId).orElse(null);
        if (employeeToBeUpdated == null) {
            return null;
        }
        // move logic
        Employee employeeWithChanges = new Employee(
                employeeId == null ? employeeToBeUpdated.getId() : employeeId,
                employeeTobeUpdated.getName()  == null ? employeeToBeUpdated.getName() : employeeTobeUpdated.getName(),
                (Integer) employeeTobeUpdated.getAge() == null ? employeeToBeUpdated.getAge() : employeeTobeUpdated.getAge(),
                employeeTobeUpdated.getGender() == null ? employeeToBeUpdated.getGender() : employeeTobeUpdated.getGender(),
                (Integer) employeeTobeUpdated.getSalary() == null ? employeeToBeUpdated.getSalary() : employeeTobeUpdated.getSalary()
        );
        employeeRepository.save(employeeWithChanges);
        return employeeToBeUpdated;
    }
}

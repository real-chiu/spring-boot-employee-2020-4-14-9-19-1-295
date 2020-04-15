package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        employees.add(new Employee(0, "Xiaoming", 20, "Male", 5000));
        employees.add(new Employee(1, "Xiaohong", 19, "Male", 5000));
        employees.add(new Employee(2, "Xiaozhi", 15, "Male", 5000));
        employees.add(new Employee(3, "Xiaoxia", 16, "Female", 5000));
    }

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String gender,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer pageSize) {
        if (gender == null) {
            return new ResponseEntity<>(pagingEmployeeList(employees, page, pageSize), HttpStatus.OK);
        }
        List<Employee> maleEmployees =  employees.stream().filter(employee -> employee.getGender().toLowerCase().equals(gender)).collect(Collectors.toList());
        return new ResponseEntity<>(pagingEmployeeList(maleEmployees, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getEmployees(@PathVariable int employeeId) {
        Employee specificEmployee =  employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
        if (specificEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(specificEmployee, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> createNewEmployee(@RequestBody Employee employeeTobeAdded) {
        Employee newAddedEmployee = employees.stream().filter(employee -> employeeTobeAdded.getId() == employee.getId()).findFirst().orElse(null);
        if (newAddedEmployee != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        employees.add(employeeTobeAdded);
        return new ResponseEntity<>(employeeTobeAdded, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int employeeId) {
        Employee deletedEmployee = employees.stream().filter(employee -> employeeId == employee.getId()).findFirst().orElse(null);
        if (deletedEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        employees = employees.stream().filter(employee -> employeeId != employee.getId()).collect(Collectors.toList());
        return new ResponseEntity<>(deletedEmployee, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee newEmployee) {
        Employee updatedEmployee = employees.stream().filter(employee -> employeeId == employee.getId()).findFirst().orElse(null);
        if (updatedEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        employees = employees.stream().map(employee -> {
            if(employee.getId() == employeeId) {
                return newEmployee;
            }
            return employee;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}

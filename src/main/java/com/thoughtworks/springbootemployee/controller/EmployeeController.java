package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String gender,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer pageSize) {
        List<Employee> allEmployees = employeeService.getAllEmployees(gender, page, pageSize);
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeId) {
        Employee specifiedEmployee = employeeService.getEmployeeById(employeeId);
        if (specifiedEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(specifiedEmployee, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employeeTobeAdded) {
        Employee newAddedEmployee = employeeService.addNewEmployee(employeeTobeAdded);
        if (newAddedEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeTobeAdded, HttpStatus.CREATED);
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
    public  ResponseEntity<Employee> updateEmployee(@PathVariable Integer employeeId,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) Integer age,
                                                    @RequestParam(required = false) String gender,
                                                    @RequestParam(required = false) Integer salary) {
        Employee employeeToBeUpdated = employees.stream().filter(employee -> employeeId == employee.getId()).findFirst().orElse(null);
        if (employeeToBeUpdated == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Employee employeeWithChanges = new Employee(
                employeeId == null ? employeeToBeUpdated.getId() : employeeId,
                name  == null ? employeeToBeUpdated.getName() : name,
                age == null ? employeeToBeUpdated.getAge() : age,
                gender == null ? employeeToBeUpdated.getGender() : gender,
                salary == null ? employeeToBeUpdated.getSalary() : salary
        );
        employees = employees.stream().map(employee -> {
            if(employee.getId() == employeeId) {
                return employeeWithChanges;
            }
            return employee;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(employeeWithChanges, HttpStatus.OK);
    }
}

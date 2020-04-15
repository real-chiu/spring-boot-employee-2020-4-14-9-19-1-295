package com.thoughtworks.springbootemployee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        employees.add(new Employee(0, "Xiaoming", 20, "Male"));
        employees.add(new Employee(1, "Xiaohong", 19, "Male"));
        employees.add(new Employee(2, "Xiaozhi", 15, "Male"));
        employees.add(new Employee(3, "Xiaoxia", 16, "Female"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String gender) {
        if (gender == null) {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        System.out.println(gender);
        List<Employee> maleEmployees =  employees.stream().filter(employee -> employee.getGender().toLowerCase().equals(gender)).collect(Collectors.toList());
        return new ResponseEntity<>(maleEmployees, HttpStatus.OK);
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

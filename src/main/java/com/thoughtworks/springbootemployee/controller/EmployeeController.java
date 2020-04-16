package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

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
        Employee deletedEmployee = employeeService.deleteEmployee(employeeId);
        if (deletedEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedEmployee, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Employee> updateEmployee(@PathVariable Integer employeeId,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) Integer age,
                                                    @RequestParam(required = false) String gender,
                                                    @RequestParam(required = false) Integer salary) {
        Employee employeeToBeUpdated = employeeService.updateEmployee(employeeId, name, age, gender, salary);
        if (employeeToBeUpdated == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeToBeUpdated, HttpStatus.OK);
    }
}

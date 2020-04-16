package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private List<Employee> employees = new ArrayList<>();

    private Employee employeeToBeAdded = new Employee(4, "New comer", 23, "Male", 5000);
    private Employee modifiedEmployee = new Employee(1, "HelloWorld", 30, "Male", 5000);
    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(new EmployeeController(employeeService));
        employees.add(new Employee(0, "Xiaoming", 20, "Male", 5000));
        employees.add(new Employee(1, "Xiaohong", 19, "Male", 5000));
        employees.add(new Employee(2, "Xiaozhi", 15, "Male", 5000));
        employees.add(new Employee(3, "Xiaoxia", 16, "Female", 5000));

    }
    @Test
    public void shouldAbleToFindEmployeeById() {
        doReturn(employees.get(1)).when(employeeService).getEmployeeById(1);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");
        Assert.assertEquals(200, mockResponse.getStatusCode());

        Employee employee = mockResponse.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
        Assert.assertEquals("Xiaohong", employee.getName());
    }

    @Test
    public void shouldAbleToFindAllEmployee() {
        doReturn(employees).when(employeeService).getAllEmployees(null, null, null);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        List<Employee> employees = mockResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(4, employees.size());
    }

    @Test
    public void shouldAbleToFindAllMaleEmployee() {
        doReturn(employees.subList(0, 3)).when(employeeService).getAllEmployees("male", null, null);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees?gender=male");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        List<Employee> employees = mockResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals(true, employees.stream().allMatch(employee -> "Male".equals(employee.getGender())));
    }

    @Test
    public void shouldAbleToFindAllEmployeeWithPaging() {
        doReturn(employees.subList(1,2)).when(employeeService).getAllEmployees(null, 2, 1);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees?page=2&pageSize=1");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        List<Employee> employees = mockResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals(1, employees.get(0).getId());
    }

    @Test
    public void shouldAbleToAddEmployeeAndReturnAddedEmployee() {
        doReturn(employeeToBeAdded).when(employeeService).addNewEmployee(any());

        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .body(employeeToBeAdded)
                .when()
                .post("/employees");
        Assert.assertEquals(201, mockResponse.getStatusCode());

        Employee addedEmployee = mockResponse.getBody().as(Employee.class);
        Assert.assertEquals(4, addedEmployee.getId());
        Assert.assertEquals("New comer", addedEmployee.getName());
    }

    @Test
    public void shouldAbleToDeleteEmployeeAndReturnDeletedEmployee() {
        doReturn(employees.get(1)).when(employeeService).deleteEmployee(1);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/1");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        Employee deletedEmployee = mockResponse.getBody().as(Employee.class);
        Assert.assertEquals(1, deletedEmployee.getId());
        Assert.assertEquals("Xiaohong", deletedEmployee.getName());
        Assert.assertEquals(19, deletedEmployee.getAge());
    }

    @Test
    public void shouldAbleToModifyEmployee() {
        doReturn(modifiedEmployee).when(employeeService).updateEmployee(1, "HelloWorld", 30, null, null);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .put("/employees/1?name=HelloWorld&age=30");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        Employee employeeToBeModified = mockResponse.getBody().as(Employee.class);
        Employee modifiedEmployee = mockResponse.getBody().as(Employee.class);
        Assert.assertEquals(modifiedEmployee.getId(), employeeToBeModified.getId());
        Assert.assertEquals(modifiedEmployee.getName(), employeeToBeModified.getName());
        Assert.assertEquals(modifiedEmployee.getAge(), employeeToBeModified.getAge());
    }
}

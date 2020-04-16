package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void shouldAbleToFindEmployeeById() {
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
        Employee employeeToBeAdded = new Employee(4, "New comer", 23, "Male", 5000);

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

        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/1");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        Employee deletedEmployee = mockResponse.getBody().as(Employee.class);
        Assert.assertEquals(1, deletedEmployee.getId());
        Assert.assertEquals("Xiaohong", deletedEmployee.getName());
        Assert.assertEquals(19, deletedEmployee.getAge());
    }
}

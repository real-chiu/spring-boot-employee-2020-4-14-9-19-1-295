package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    private List<Company> companies = new ArrayList<>();
    private Company company = new Company(0, "OOCL", 0, new ArrayList<>());
    private Company companyTwo = new Company(1, "CargoSmart", 0, new ArrayList<>());
    private Company companyToBeAdded = new Company(2, "Alibaba", 0, new ArrayList<>());
    private Company modifiedCompany = new Company(2, "Alibaba", 0, new ArrayList<>());

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(new CompanyController(companyService));
        List<Employee> companyEmployees = new ArrayList<>();
        companyEmployees.add(new Employee(0, "Xiaoming", 20, "Male", 5000, 0, null));
        companyEmployees.add(new Employee(0, "Xiaoming", 20, "Male", 5000, 0, null));
        companyEmployees.add(new Employee(1, "Xiaohong", 19, "Male", 7000, 0, null));
        companyEmployees.add(new Employee(2, "Xiaozhi", 15, "Male", 9000, 0, null));
        companyEmployees.add(new Employee(3, "Xiaoxia", 16, "Female", 10000, 0, null));
        company.setEmployees(companyEmployees);
        company.setEmployeesNumber(4);

        List<Employee> companyTwoEmployees = new ArrayList<>();
        companyTwoEmployees.add(new Employee(4, "A", 20, "Male", 5000, 1, null));
        companyTwoEmployees.add(new Employee(5, "B", 19, "Male", 7000, 0,null));
        companyTwoEmployees.add(new Employee(6, "C", 15, "Male", 9000, 0, null));
        companyTwoEmployees.add(new Employee(7, "D", 16, "Female", 10000, 0, null));
        companyTwo.setEmployees(companyTwoEmployees);
        companyTwo.setEmployeesNumber(4);

        List<Employee> companyToBeAddedEmployees = new ArrayList<>();
        companyToBeAddedEmployees.add(new Employee(8, "A", 20, "Male", 5000, 0, null));
        companyToBeAddedEmployees.add(new Employee(9, "A", 20, "Male", 5000, 0, null));
        companyToBeAddedEmployees.add(new Employee(10, "A", 20, "Male", 5000, 0, null));
        companyToBeAddedEmployees.add(new Employee(11, "A", 20, "Male", 5000, 0, null));
        companyToBeAddedEmployees.add(new Employee(12, "A", 20, "Male", 5000, 0, null));
        companyToBeAdded.setEmployees(companyToBeAddedEmployees);
        companyToBeAdded.setEmployeesNumber(5);

        List<Employee> companyToBeUpdatedEmployees = new ArrayList<>();

        companyToBeUpdatedEmployees.add(new Employee(9, "A", 20, "Male", 5000, 2, null));
        companyToBeUpdatedEmployees.add(new Employee(10, "A", 20, "Male", 5000, 2, null));
        modifiedCompany.setEmployees(companyToBeUpdatedEmployees);
        modifiedCompany.setEmployeesNumber(2);

        companies.add(company);
        companies.add(companyTwo);
    }

    @Test
    public void shouldAbleToFindCompanyById() {
        doReturn(companies.get(1)).when(companyService).getCompanyById(1);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");
        Assert.assertEquals(200, mockResponse.getStatusCode());

        Company company = mockResponse.getBody().as(Company.class);
        Assert.assertEquals(1, company.getId());
        Assert.assertEquals("CargoSmart", company.getCompanyName());
    }

    @Test
    public void shouldAbleToFindAllCompany() {
        doReturn(companies).when(companyService).getAllCompany(null, null);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies");
        Assert.assertEquals(200, mockResponse.getStatusCode());

        List<Company> companies = mockResponse.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("OOCL", companies.get(0).getCompanyName());
        Assert.assertEquals("CargoSmart", companies.get(1).getCompanyName());
    }

    @Test
    public void shouldAbleToFindAllCompanyWithPaging() {
        doReturn(companies.subList(1, 2)).when(companyService).getAllCompany(2, 1);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies?page=2&pageSize=1");
        Assert.assertEquals(200, mockResponse.getStatusCode());

        List<Company> companies = mockResponse.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, companies.size());
        Assert.assertEquals("CargoSmart", companies.get(0).getCompanyName());
    }

    @Test
    public void shouldAbleToFindAllEmployeesOfCompanyWithCompanyId() {
        doReturn(companyTwo.getEmployees()).when(companyService).getEmployeeOfCompanyByCompanyId(1);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1/employees");
        Assert.assertEquals(200, mockResponse.getStatusCode());

        List<Employee> employeesOfCompany = mockResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(4, employeesOfCompany.size());
    }

    @Test
    public void shouldAbleToAddNewCompany() {
        doReturn(companyToBeAdded).when(companyService).addNewCompany(any());
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .body(companyToBeAdded)
                .when()
                .post("/companies");
        Assert.assertEquals(201, mockResponse.getStatusCode());

        Company addedCompany = mockResponse.getBody().as(Company.class);
        Assert.assertEquals(2, addedCompany.getId());
        Assert.assertEquals("Alibaba", addedCompany.getCompanyName());
    }

    @Test
    public void shouldAbleToDeleteCompanyAndReturnDeletedCompany() {
        doReturn(companyTwo).when(companyService).deleteCompany(1);
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        Company deletedCompany = mockResponse.getBody().as(Company.class);
        Assert.assertEquals(1, deletedCompany.getId());
    }

    @Test
    public void shouldAbleToModifyCompany() {
        doReturn(modifiedCompany).when(companyService).updateCompany(any(), any(), any(), any());
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .body(modifiedCompany)
                .when()
                .put("/companies/1");

        Assert.assertEquals(200, mockResponse.getStatusCode());

        Company companyToBeModified = mockResponse.getBody().as(Company.class);

        Assert.assertEquals(2, companyToBeModified.getId());
        Assert.assertEquals("Alibaba", companyToBeModified.getCompanyName());
        Assert.assertEquals(2, companyToBeModified.getEmployeesNumber());
    }
}

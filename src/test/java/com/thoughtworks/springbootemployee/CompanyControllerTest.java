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
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    private List<Company> companies = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(new CompanyController(companyService));
        Company company = new Company(0, "OOCL", 0, new ArrayList<>());
        company.addEmployee(new Employee(0, "Xiaoming", 20, "Male", 5000));
        company.addEmployee(new Employee(1, "Xiaohong", 19, "Male", 7000));
        company.addEmployee(new Employee(2, "Xiaozhi", 15, "Male", 9000));
        company.addEmployee(new Employee(3, "Xiaoxia", 16, "Female", 10000));

        Company companyTwo = new Company(1, "CargoSmart", 0, new ArrayList<>());
        companyTwo.addEmployee(new Employee(4, "A", 20, "Male", 5000));
        companyTwo.addEmployee(new Employee(5, "B", 19, "Male", 7000));
        companyTwo.addEmployee(new Employee(6, "C", 15, "Male", 9000));
        companyTwo.addEmployee(new Employee(7, "D", 16, "Female", 10000));

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
        RestAssuredMockMvc.standaloneSetup(new CompanyController(companyService));
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
}

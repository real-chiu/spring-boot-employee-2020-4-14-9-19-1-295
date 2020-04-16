package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Test
    public void shouldAbleToFindCompanyById() {
        RestAssuredMockMvc.standaloneSetup(new CompanyController());
        MockMvcResponse mockResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");
        Assert.assertEquals(200, mockResponse.getStatusCode());

        Company company = mockResponse.getBody().as(Company.class);
        Assert.assertEquals(1, company.getId());
        Assert.assertEquals("CargoSmart", company.getCompanyName());
    }
}
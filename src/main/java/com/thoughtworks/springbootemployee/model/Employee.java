package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;
    @OneToOne()
    @JoinColumn(name = "parkingBoyId", referencedColumnName = "id")
    private ParkingBoy parkingBoy;

    public void update(Employee employeeDetailsTobeUpdated) {
        // move logic
        if (employeeDetailsTobeUpdated.getName()  != null) {
            setName(employeeDetailsTobeUpdated.getName());
        }
        if (employeeDetailsTobeUpdated.getAge() != null) {
            setAge(employeeDetailsTobeUpdated.getAge());
        }
        if (employeeDetailsTobeUpdated.getGender()  != null) {
            setGender(employeeDetailsTobeUpdated.getGender());
        }
        if (employeeDetailsTobeUpdated.getSalary()  != null) {
            setSalary(employeeDetailsTobeUpdated.getSalary());
        }
        if (employeeDetailsTobeUpdated.getCompanyId()  != null) {
            setCompanyId(employeeDetailsTobeUpdated.getCompanyId());
        }
        if (employeeDetailsTobeUpdated.getParkingBoy()  != null) {
            setParkingBoy(employeeDetailsTobeUpdated.getParkingBoy());
        }
    }
}

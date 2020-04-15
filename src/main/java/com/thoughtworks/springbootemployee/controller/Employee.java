package com.thoughtworks.springbootemployee.controller;

public class Employee {
    public int id;
    public String name;
    public int age;
    public String gender;

    public Employee(int id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}

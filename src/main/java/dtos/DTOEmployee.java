package dtos;

import entities.*;

public class DTOEmployee {
    private String name;
    private String address;
    private Integer salary;

    public DTOEmployee(){

    }

    public DTOEmployee (Employee employee){
        this.name = employee.getName();
        this.address = employee.getAddress();
        this.salary = employee.getSalary();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}

package com.pp_labs.lab_02;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Employee {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate birthDate;
    private String role;

    public Employee(int vId, String vName, LocalDate vStartDate, LocalDate vbirthDate, String vRole){
        this.id = vId;
        this.name = vName;
        this.startDate = vStartDate;
        this.birthDate = vbirthDate;
        this.role = vRole;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", birthDate=" + birthDate +
                ", role='" + role + '\'' +
                '}';
    }
}

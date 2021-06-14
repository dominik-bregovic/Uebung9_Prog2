package org.shen.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "salary", schema = "dbd_employee", catalog = "")
public class SalaryEntity {
    private int id;
    private String firstname;
    private String lastname;
    private String department;
    private int amount;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryEntity that = (SalaryEntity) o;
        return id == that.id && amount == that.amount && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, department, amount);
    }
}

package com.eazitasc.entity;

import com.eazitasc.enumtype.Gender;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mt_employee", uniqueConstraints = {@UniqueConstraint(columnNames = {"short_name"}), @UniqueConstraint(columnNames = {"company_email"}), @UniqueConstraint(columnNames = {"personal_email"}), @UniqueConstraint(columnNames = {"mobile_phone"})})
@IdClass(Mt_employee.Pk.class)
public class Mt_employee extends Auditable_entity {

    public Mt_employee(){
    }

    @Id
    @Column(name = "employee_code", nullable = false, columnDefinition = "varchar(20)")
    private String employee_code;

    @Column(name = "short_name", nullable = false, columnDefinition = "varchar(100)")
    private String short_name;

    @Column(name = "full_name", nullable = false, columnDefinition = "varchar(100)")
    private String full_name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", columnDefinition = "date")
    private Date date_of_birth;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_joining", columnDefinition = "date")
    private Date date_of_joining;

    @Column(name = "company_email", columnDefinition = "varchar(150)")
    private String company_email;

    @Column(name = "personal_email", columnDefinition = "varchar(150)")
    private String personal_email;

    @Column(name = "address", columnDefinition = "varchar(4000)")
    private String address;

    @Column(name = "mobile_phone", columnDefinition = "varchar(30)")
    private String mobile_phone;

    public String getEmployee_code() {
        return this.employee_code;
    }

    public void setEmployee_code(final String employee_code) {
        this.employee_code = employee_code;
    }

    public String getShort_name() {
        return this.short_name;
    }

    public void setShort_name(final String short_name) {
        this.short_name = short_name;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public void setFull_name(final String full_name) {
        this.full_name = full_name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Date getDate_of_birth() {
        return this.date_of_birth;
    }

    public void setDate_of_birth(final Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Date getDate_of_joining() {
        return this.date_of_joining;
    }

    public void setDate_of_joining(final Date date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public String getCompany_email() {
        return this.company_email;
    }

    public void setCompany_email(final String company_email) {
        this.company_email = company_email;
    }

    public String getPersonal_email() {
        return this.personal_email;
    }

    public void setPersonal_email(final String personal_email) {
        this.personal_email = personal_email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getMobile_phone() {
        return this.mobile_phone;
    }

    public void setMobile_phone(final String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public static class Pk implements Serializable {
        private String employee_code;

        public Pk(){
        }

        public Pk(String employee_code) {
            this.employee_code = employee_code;
        }

        public String getEmployee_code() {
            return this.employee_code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return Objects.equals(employee_code, pk.employee_code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(employee_code);
        }
    }

}

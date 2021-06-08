package com.eazitasc.entity;

import com.eazitasc.enumtype.GenderEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mt_employee", uniqueConstraints = {@UniqueConstraint(columnNames = {"short_name"}), @UniqueConstraint(columnNames = {"company_email"}), @UniqueConstraint(columnNames = {"personal_email"}), @UniqueConstraint(columnNames = {"mobile_phone"})})
@IdClass(Mt_employee.Pk.class)
public class Mt_employee extends Auditable_entity {

    @Id
    @Column(name = "employee_code", nullable = false)
    private String employee_code;

    @Column(name = "short_name", nullable = false)
    private String short_name;

    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date date_of_birth;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_joining")
    private Date date_of_joining;

    @Column(name = "company_email")
    private String company_email;

    @Column(name = "personal_email")
    private String personal_email;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_phone")
    private String mobile_phone;

    @OneToMany(targetEntity = Mt_department.class, mappedBy = "chiefOfDepartment")
    public List<Mt_department> listOfMt_department;

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

    public GenderEnum getGender() {
        return this.gender;
    }

    public void setGender(final GenderEnum gender) {
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

        public Pk(String employee_code) {
            this.employee_code = employee_code;
        }

        public String getEmployee_code() {
            return this.employee_code;
        }
    }

}

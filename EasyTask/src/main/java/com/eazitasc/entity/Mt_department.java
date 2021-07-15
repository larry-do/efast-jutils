package com.eazitasc.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mt_department", uniqueConstraints = {@UniqueConstraint(columnNames = {"department_name"}), @UniqueConstraint(columnNames = {"abbreviationn"})})
@IdClass(Mt_department.Pk.class)
public class Mt_department extends Auditable_entity {

    public Mt_department(){
    }

    @Id
    @Column(name = "department_code", nullable = false, columnDefinition = "varchar(20)")
    private String department_code;

    @Column(name = "department_name", nullable = false, columnDefinition = "varchar(100)")
    private String department_name;

    @Column(name = "abbreviationn", nullable = false, columnDefinition = "varchar(20)")
    private String abbreviationn;

    @Column(name = "department_desc", columnDefinition = "varchar(4000)")
    private String department_desc;

    @Column(name = "chief_code", columnDefinition = "varchar(20)")
    private String chief_code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="chief_code", referencedColumnName = "employee_code", insertable = false, updatable = false)
    })
    public Mt_employee chiefOfDepartment;

    public String getDepartment_code() {
        return this.department_code;
    }

    public void setDepartment_code(final String department_code) {
        this.department_code = department_code;
    }

    public String getDepartment_name() {
        return this.department_name;
    }

    public void setDepartment_name(final String department_name) {
        this.department_name = department_name;
    }

    public String getAbbreviationn() {
        return this.abbreviationn;
    }

    public void setAbbreviationn(final String abbreviationn) {
        this.abbreviationn = abbreviationn;
    }

    public String getDepartment_desc() {
        return this.department_desc;
    }

    public void setDepartment_desc(final String department_desc) {
        this.department_desc = department_desc;
    }

    public String getChief_code() {
        return this.chief_code;
    }

    public void setChief_code(final String chief_code) {
        this.chief_code = chief_code;
    }

    public static class Pk implements Serializable {
        private String department_code;

        public Pk(){
        }

        public Pk(String department_code) {
            this.department_code = department_code;
        }

        public String getDepartment_code() {
            return this.department_code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return Objects.equals(department_code, pk.department_code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(department_code);
        }
    }

}

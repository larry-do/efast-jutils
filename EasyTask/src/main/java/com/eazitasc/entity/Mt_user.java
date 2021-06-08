package com.eazitasc.entity;

import java.io.Serializable;
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
@Table(name = "mt_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}), @UniqueConstraint(columnNames = {"employee_code"})})
@IdClass(Mt_user.Pk.class)
public class Mt_user extends Auditable_entity {

    @Id
    @Column(name = "user_code", nullable = false, columnDefinition = "varchar(20)")
    private String user_code;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(100)")
    private String username;

    @Column(name = "encrypted_password", nullable = false, columnDefinition = "varchar(100)")
    private String encrypted_password;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean")
    private Boolean is_active = false;

    @Column(name = "employee_code", nullable = false, columnDefinition = "varchar(20)")
    private String employee_code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="employee_code", referencedColumnName = "employee_code", insertable = false, updatable = false)
    })
    public Mt_employee mt_employee;

    public String getUser_code() {
        return this.user_code;
    }

    public void setUser_code(final String user_code) {
        this.user_code = user_code;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEncrypted_password() {
        return this.encrypted_password;
    }

    public void setEncrypted_password(final String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public Boolean getIs_active() {
        return this.is_active;
    }

    public void setIs_active(final Boolean is_active) {
        this.is_active = is_active;
    }

    public String getEmployee_code() {
        return this.employee_code;
    }

    public void setEmployee_code(final String employee_code) {
        this.employee_code = employee_code;
    }

    public static class Pk implements Serializable {
        private String user_code;

        public Pk(String user_code) {
            this.user_code = user_code;
        }

        public String getUser_code() {
            return this.user_code;
        }
    }

}

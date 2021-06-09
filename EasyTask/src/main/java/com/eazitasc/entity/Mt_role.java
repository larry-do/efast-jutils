package com.eazitasc.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mt_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"role_name"})})
@IdClass(Mt_role.Pk.class)
public class Mt_role extends Auditable_entity {

    public Mt_role(){
    }

    @Id
    @Column(name = "role_code", nullable = false, columnDefinition = "varchar(20)")
    private String role_code;

    @Column(name = "role_name", nullable = false, columnDefinition = "varchar(100)")
    private String role_name;

    public String getRole_code() {
        return this.role_code;
    }

    public void setRole_code(final String role_code) {
        this.role_code = role_code;
    }

    public String getRole_name() {
        return this.role_name;
    }

    public void setRole_name(final String role_name) {
        this.role_name = role_name;
    }

    public static class Pk implements Serializable {
        private String role_code;

        public Pk(){
        }

        public Pk(String role_code) {
            this.role_code = role_code;
        }

        public String getRole_code() {
            return this.role_code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return Objects.equals(role_code, pk.role_code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(role_code);
        }
    }

}

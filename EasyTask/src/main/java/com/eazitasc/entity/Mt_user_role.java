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

@Entity
@Table(name = "mt_user_role")
@IdClass(Mt_user_role.Pk.class)
public class Mt_user_role extends Auditable_entity {

    public Mt_user_role(){
    }

    @Id
    @Column(name = "user_code", nullable = false, columnDefinition = "varchar(20)")
    private String user_code;

    @Id
    @Column(name = "role_code", nullable = false, columnDefinition = "varchar(20)")
    private String role_code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
    })
    public Mt_user mt_user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="role_code", referencedColumnName = "role_code", insertable = false, updatable = false)
    })
    public Mt_role mt_role;

    public String getUser_code() {
        return this.user_code;
    }

    public void setUser_code(final String user_code) {
        this.user_code = user_code;
    }

    public String getRole_code() {
        return this.role_code;
    }

    public void setRole_code(final String role_code) {
        this.role_code = role_code;
    }

    public static class Pk implements Serializable {
        private String user_code;

        private String role_code;

        public Pk(){
        }

        public Pk(String user_code, String role_code) {
            this.user_code = user_code;
            this.role_code = role_code;
        }

        public String getUser_code() {
            return this.user_code;
        }

        public String getRole_code() {
            return this.role_code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return Objects.equals(user_code, pk.user_code) && Objects.equals(role_code, pk.role_code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user_code, role_code);
        }
    }

}

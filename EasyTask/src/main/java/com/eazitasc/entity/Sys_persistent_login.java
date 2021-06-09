package com.eazitasc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "sys_persistent_login", uniqueConstraints = {@UniqueConstraint(columnNames = {"token"})})
@IdClass(Sys_persistent_login.Pk.class)
public class Sys_persistent_login {

    public Sys_persistent_login(){
    }

    @Id
    @Column(name = "series", nullable = false, columnDefinition = "varchar(100)")
    private String series;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(100)")
    private String username;

    @Column(name = "token", nullable = false, columnDefinition = "varchar(100)")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used", nullable = false, columnDefinition = "date")
    private Date last_used;

    public String getSeries() {
        return this.series;
    }

    public void setSeries(final String series) {
        this.series = series;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Date getLast_used() {
        return this.last_used;
    }

    public void setLast_used(final Date last_used) {
        this.last_used = last_used;
    }

    public static class Pk implements Serializable {
        private String series;

        public Pk(){
        }

        public Pk(String series) {
            this.series = series;
        }

        public String getSeries() {
            return this.series;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return Objects.equals(series, pk.series);
        }

        @Override
        public int hashCode() {
            return Objects.hash(series);
        }
    }

}

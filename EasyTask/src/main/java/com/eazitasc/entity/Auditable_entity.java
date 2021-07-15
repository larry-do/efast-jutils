package com.eazitasc.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class Auditable_entity {

    public Auditable_entity(){
    }

    @Column(name = "created_by", columnDefinition = "varchar(20)")
    private String created_by;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime", columnDefinition = "date")
    private Date created_datetime = new Date();

    @Column(name = "last_updated_by", columnDefinition = "varchar(20)")
    private String last_updated_by;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_datetime", columnDefinition = "date")
    private Date last_updated_datetime;

    @Version
    @Column(name = "audit_version", columnDefinition = "integer")
    private Integer audit_version = 0;

    public String getCreated_by() {
        return this.created_by;
    }

    public void setCreated_by(final String created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_datetime() {
        return this.created_datetime;
    }

    public void setCreated_datetime(final Date created_datetime) {
        this.created_datetime = created_datetime;
    }

    public String getLast_updated_by() {
        return this.last_updated_by;
    }

    public void setLast_updated_by(final String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public Date getLast_updated_datetime() {
        return this.last_updated_datetime;
    }

    public void setLast_updated_datetime(final Date last_updated_datetime) {
        this.last_updated_datetime = last_updated_datetime;
    }

    public Integer getAudit_version() {
        return this.audit_version;
    }

    public void setAudit_version(final Integer audit_version) {
        this.audit_version = audit_version;
    }

}

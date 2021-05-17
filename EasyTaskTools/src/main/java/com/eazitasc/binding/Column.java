package com.eazitasc.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Getter
@Setter
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "table-column")
public class Column {
    @XmlAttribute(required = true)
    private String name;

    @XmlAttribute(required = true)
    private String type;

    @XmlAttribute
    private Boolean nullable;

    @XmlAttribute(name = "default")
    private String defaultValue;

    @XmlTransient
    private String jvType;

    @XmlTransient
    private String dbType;

    @XmlTransient
    private Boolean isPrimaryKey;

    @XmlTransient
    private Boolean isEnum;
}

package com.eazitasc.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Getter
@Setter
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Column {
    @XmlAttribute(required = true)
    private String name;

    @XmlAttribute
    private String propertyName;

    @XmlAttribute(required = true)
    private String type;

    @XmlTransient
    private String jvType;

    @XmlTransient
    private String dbType;

    @XmlAttribute
    private Boolean nullable;

    @XmlAttribute(name = "default")
    private String defaultValue;
}

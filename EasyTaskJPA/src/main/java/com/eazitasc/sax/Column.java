package com.eazitasc.sax;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Column {
    @XmlAttribute
    private String name;

    @XmlAttribute
    private String propertyName;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private Boolean nullable;

    @XmlAttribute
    private String defaultValue;

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", type='" + type + '\'' +
                ", nullable=" + nullable +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}

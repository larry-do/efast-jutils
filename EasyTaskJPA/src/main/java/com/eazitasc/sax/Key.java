package com.eazitasc.sax;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Key {
    @XmlAttribute
    private String type;

    @XmlAttribute
    private String targetTable;

    @XmlAttribute
    private String fieldName;

    @XmlAttribute
    private String fetch;

    @XmlAttribute
    private String columns;

    @XmlElement
    private Mapping mapping;

    @Override
    public String toString() {
        return "Key{" +
                "type='" + type + '\'' +
                ", targetTable='" + targetTable + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fetch='" + fetch + '\'' +
                ", columns='" + columns + '\'' +
                ", mapping=" + mapping +
                '}';
    }

    @Getter
    @Setter
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Mapping {
        @XmlAttribute
        private String fieldName;

        @XmlAttribute
        private String fetch;

        @XmlAttribute
        private String orderBy;

        @Override
        public String toString() {
            return "Mapping{" +
                    "fieldName='" + fieldName + '\'' +
                    ", fetch='" + fetch + '\'' +
                    ", orderBy='" + orderBy + '\'' +
                    '}';
        }
    }
}

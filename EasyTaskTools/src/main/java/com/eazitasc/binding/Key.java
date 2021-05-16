package com.eazitasc.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Arrays;
import java.util.LinkedHashSet;

@Getter
@Setter
@ToString
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

    public LinkedHashSet<String> getColumnsOfKey() {
        if (StringUtils.isEmpty(this.columns)) return new LinkedHashSet<>();
        else {
            return new LinkedHashSet<>(Arrays.asList(columns.replace(" ", "").split(",")));
        }
    }

    @Getter
    @Setter
    @ToString
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Mapping {
        @XmlAttribute
        private String fieldName;

        @XmlAttribute
        private String fetch;

        @XmlAttribute
        private String orderBy;

        @XmlTransient
        private String fromTable;

        @XmlTransient
        private String mappedBy;
    }
}

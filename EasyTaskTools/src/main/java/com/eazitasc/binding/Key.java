package com.eazitasc.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public Set<String> getColumnsOfKey() {
        if (StringUtils.isEmpty(this.columns)) return new HashSet<>();
        else {
            return new HashSet<>(Arrays.asList(columns.replace(" ", "").split(",")));
        }
    }

    @Getter
    @Setter
    @ToString
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    static public class Mapping {
        @XmlAttribute
        private String fieldName;

        @XmlAttribute
        private String fetch;

        @XmlAttribute
        private String orderBy;
    }
}

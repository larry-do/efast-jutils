package com.kwicktask.utils.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

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

    @XmlAttribute(name = "enum")
    private Boolean isEnum;

    @XmlTransient
    private Set<String> otherAnnotations = new HashSet<>();
}

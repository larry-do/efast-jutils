package com.efasttask.util.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Getter
@Setter
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {
    @XmlAttribute(required = true)
    private String name;

    @XmlAttribute
    private String parent;

    @XmlAttribute(name = "abstract")
    private Boolean isAbstract;

    @XmlAttribute
    private String repository;

    @XmlElement(name = "column")
    private List<Column> columns;

    @XmlElement(name = "primary-key")
    private PrimaryKey primaryKey;

    @XmlElement(name = "unique-constraint")
    private List<UniqueConstraint> uniqueConstraints = new ArrayList<>();

    @XmlElement(name = "foreign-key")
    private List<ForeignKey> foreignKeys = new ArrayList<>();

    @XmlTransient
    private LinkedHashSet<ForeignKey.TargetMapping> inverseMappings = new LinkedHashSet<>();

    public Column getColumn(String name) {
        if (this.columns == null) return null;
        else return this.columns.stream().filter(col -> col.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

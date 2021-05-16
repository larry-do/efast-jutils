package com.eazitasc.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @XmlElement
    private LinkedHashSet<Column> column;

    @XmlElement
    private LinkedHashSet<Key> key;

    @XmlTransient
    private String className;

    @XmlTransient
    private LinkedHashSet<Key> primaryKeys;

    @XmlTransient
    private LinkedHashSet<Key> foreignKeys;

    @XmlTransient
    private LinkedHashSet<Key> uniqueConstraints;

    public Column getColumn(String columnName) {
        if (this.column == null) return null;
        else return this.column.stream().filter(col -> col.getName().equalsIgnoreCase(columnName)).findFirst().orElse(null);
    }
}

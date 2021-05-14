package com.eazitasc.sax;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedHashSet;

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {
    @XmlAttribute
    private String name;

    @XmlAttribute
    private String className;

    @XmlAttribute
    private String parent;

    @XmlAttribute
    private Boolean isAstract;

    @XmlElement
    private LinkedHashSet<Column> column;

    @XmlElement
    private LinkedHashSet<Key> key;

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", parent='" + parent + '\'' +
                ", isAstract=" + isAstract +
                ", column=" + column +
                ", key=" + key +
                '}';
    }
}

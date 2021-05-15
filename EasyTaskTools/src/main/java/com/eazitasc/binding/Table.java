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
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {
    @XmlAttribute(required = true)
    private String name;

    @XmlTransient
    private String className;

    @XmlAttribute
    private String parent;

    @XmlAttribute(name = "abstract")
    private Boolean isAbstract;

    @XmlElement
    private LinkedHashSet<Column> column;

    @XmlElement
    private LinkedHashSet<Key> key;

    public Set<Key> getUniqueConstraints() {
        if (this.key == null || this.key.isEmpty()) return null;
        else {
            return this.key.stream().filter(k -> StringUtils.isEmpty(k.getType())).collect(Collectors.toSet());
        }
    }
}

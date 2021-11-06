package com.kwicktask.utils.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@XmlRootElement(name = "foreign-key")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForeignKey {
    @XmlAttribute
    private String fieldName;

    @XmlAttribute
    private String target;

    @XmlAttribute
    private String fetch;

    @XmlElement(name = "column")
    private List<Column> columns = new ArrayList<>();

    @XmlElement(name = "target-mapping")
    private TargetMapping targetMapping;

    @Getter
    @Setter
    @ToString
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "key-column")
    public static class Column {
        @XmlAttribute
        private String name;

        @XmlAttribute
        private String target;
    }

    @Getter
    @Setter
    @ToString
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TargetMapping {
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

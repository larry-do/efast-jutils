package com.eazitasc.entity;

<#list imports as import>
${import}
</#list>

<#if table.isAbstract?? && table.isAbstract>
@MappedSuperclass
<#else>
@Entity
@Table(name = "${table.name}"<#if table.uniqueConstraints?? && table.uniqueConstraints?size gt 0>, uniqueConstraints = {<#list table.uniqueConstraints as key>@UniqueConstraint(columnNames = {<#list key.getColumnsOfKey() as col>"${col}"${col?has_next?then(", ", "")}</#list>})${key?has_next?then(", ", "")}</#list>}</#if>)
</#if>
<#if table.primaryKeys??>
<#list table.primaryKeys as key>
@IdClass(${table.name?capitalize}.Pk.class)
</#list>
</#if>
public<#if table.isAbstract?? && table.isAbstract> abstract</#if> class ${table.className}<#if table.parent??> extends ${table.parent}</#if> {

<#list table.column as col>
<#if col.isPrimaryKey??!false>
    @Id
</#if>
<#if col.type?lower_case == "version">
    @Version
</#if>
    @Column(name = "${col.name}"<#if col.nullable?? && col.nullable == false>, nullable = false</#if>)
    private ${col.jvType} ${col.propertyName};

</#list>
<#if table.foreignKeys??>
<#list table.foreignKeys as key>
    @ManyToOne<#if key.fetch?has_content>(fetch = FetchType.${key.fetch})</#if>
    @JoinColumns({
    <#list key.getColumnsOfKey() as col>
        @JoinColumn(name="${col}", referencedColumnName = "${col}")<#if col?has_next>, </#if>
    </#list>
    })
    public ${key.targetTable?capitalize} ${key.fieldName};

</#list>
</#if>
<#if table.inverseMappings??>
<#list table.inverseMappings as mapping>
<#if mapping.orderBy?has_content>
    @OrderBy("${mapping.orderBy}")
</#if>
    @OneToMany(targetEntity = void.class<#if mapping.fetch?has_content>, fetch = FetchType.${mapping.fetch}</#if><#if mapping.mappedBy?has_content>, mappedBy = "${mapping.mappedBy}"</#if>)
    public List<${mapping.fromTable}> ${mapping.fieldName};

</#list>
</#if>
<#list table.column as col>
    public ${col.jvType} get${col.propertyName?capitalize}() {
        return this.${col.propertyName};
    }

    public void set${col.propertyName?capitalize}(final ${col.jvType} ${col.propertyName}) {
        this.${col.propertyName} = ${col.propertyName};
    }

</#list>
<#if table.primaryKeys??>
<#list table.primaryKeys as key>
    public static class Pk implements Serializable {
    <#list key.getColumnsOfKey() as col>
        private ${table.getColumn(col).jvType} ${table.getColumn(col).propertyName};

    </#list>
        public Pk(<#list key.getColumnsOfKey() as col>${table.getColumn(col).jvType} ${table.getColumn(col).propertyName}<#if col?has_next>, </#if></#list>) {
        <#list key.getColumnsOfKey() as col>
            this.${table.getColumn(col).propertyName} = ${table.getColumn(col).propertyName};
        </#list>
        }

    <#list key.getColumnsOfKey() as col>
        public ${table.getColumn(col).jvType} get${table.getColumn(col).propertyName?capitalize}() {
            return this.${table.getColumn(col).propertyName};
        }

    </#list>
    }

</#list>
</#if>
}

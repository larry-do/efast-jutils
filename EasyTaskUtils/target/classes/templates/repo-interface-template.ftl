package ${package};

<#list imports as import>
${import}
</#list>

public interface ${fileName} extends EasyRepoInterface<${table.name?capitalize}, ${table.name?capitalize}.Pk> {
    ${table.name?capitalize} getByPk(<#list table.primaryKey.getColumnsOfKey() as column><#assign col = table.getColumn(column)>${col.jvType} ${col.name}${column?has_next?then(", ", "")}</#list>);
    <#list table.uniqueConstraints as key>

    ${table.name?capitalize} getBy<#list key.getColumnsOfKey() as column>${column?capitalize}</#list>(<#list key.getColumnsOfKey() as column><#assign col = table.getColumn(column)>${col.jvType} ${col.name}${column?has_next?then(", ", "")}</#list>);
    </#list>
}
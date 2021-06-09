package ${package};

<#list imports as import>
${import}
</#list>

@Repository
@Transactional
public class ${fileName} extends AbstractEasyRepo<${table.name?capitalize}, ${table.name?capitalize}.Pk> implements ${repoName} {

    @Override
    public ${table.name?capitalize} getByPk(<#list table.primaryKey.getColumnsOfKey() as column><#assign col = table.getColumn(column)>${col.jvType} ${col.name}${column?has_next?then(", ", "")}</#list>) {
        return this.getByPk(new ${table.name?capitalize}.Pk(<#list table.primaryKey.getColumnsOfKey() as column>${column}${column?has_next?then(", ", "")}</#list>));
    }

    <#list table.uniqueConstraints as key>
    @Override
    public ${table.name?capitalize} getBy<#list key.getColumnsOfKey() as column>${column?capitalize}</#list>(<#list key.getColumnsOfKey() as column><#assign col = table.getColumn(column)>${col.jvType} ${col.name}${column?has_next?then(", ", "")}</#list>) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<${table.name?capitalize}> cq = cb.createQuery(${table.name?capitalize}.class);
        Root<${table.name?capitalize}> root = cq.from(${table.name?capitalize}.class);
        cq.where(cb.and(<#list key.getColumnsOfKey() as column>cb.equal(root.get(${table.name?capitalize}_.${column}), ${column})${column?has_next?then(", ", "")}</#list>));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

    </#list>
}
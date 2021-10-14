package ${package};

public enum ${fileName} {
    <#list values as value>${value.key}("${value.value}")${value?has_next?then( ', ', '')}</#list>;

    <#list values as value>
    public static final ${fileName} ${value.value} = ${value.key};
    </#list>

    private String value;

    ${fileName}(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
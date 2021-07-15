package com.eazitasc.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mt_department.class)
public abstract class Mt_department_ extends com.eazitasc.entity.Auditable_entity_ {

	public static volatile SingularAttribute<Mt_department, String> department_code;
	public static volatile SingularAttribute<Mt_department, String> department_desc;
	public static volatile SingularAttribute<Mt_department, Mt_employee> chiefOfDepartment;
	public static volatile SingularAttribute<Mt_department, String> department_name;
	public static volatile SingularAttribute<Mt_department, String> abbreviationn;
	public static volatile SingularAttribute<Mt_department, String> chief_code;

	public static final String DEPARTMENT_CODE = "department_code";
	public static final String DEPARTMENT_DESC = "department_desc";
	public static final String CHIEF_OF_DEPARTMENT = "chiefOfDepartment";
	public static final String DEPARTMENT_NAME = "department_name";
	public static final String ABBREVIATIONN = "abbreviationn";
	public static final String CHIEF_CODE = "chief_code";

}


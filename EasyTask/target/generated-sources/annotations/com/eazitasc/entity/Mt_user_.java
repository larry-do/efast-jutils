package com.eazitasc.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mt_user.class)
public abstract class Mt_user_ extends com.eazitasc.entity.Auditable_entity_ {

	public static volatile SingularAttribute<Mt_user, Boolean> is_active;
	public static volatile SingularAttribute<Mt_user, String> user_code;
	public static volatile SingularAttribute<Mt_user, Mt_employee> mt_employee;
	public static volatile SingularAttribute<Mt_user, String> username;
	public static volatile SingularAttribute<Mt_user, String> encrypted_password;
	public static volatile SingularAttribute<Mt_user, String> employee_code;

	public static final String IS_ACTIVE = "is_active";
	public static final String USER_CODE = "user_code";
	public static final String MT_EMPLOYEE = "mt_employee";
	public static final String USERNAME = "username";
	public static final String ENCRYPTED_PASSWORD = "encrypted_password";
	public static final String EMPLOYEE_CODE = "employee_code";

}


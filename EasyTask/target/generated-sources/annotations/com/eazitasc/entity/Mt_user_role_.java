package com.eazitasc.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mt_user_role.class)
public abstract class Mt_user_role_ extends com.eazitasc.entity.Auditable_entity_ {

	public static volatile SingularAttribute<Mt_user_role, String> user_code;
	public static volatile SingularAttribute<Mt_user_role, Mt_role> mt_role;
	public static volatile SingularAttribute<Mt_user_role, Mt_user> mt_user;
	public static volatile SingularAttribute<Mt_user_role, String> role_code;

	public static final String USER_CODE = "user_code";
	public static final String MT_ROLE = "mt_role";
	public static final String MT_USER = "mt_user";
	public static final String ROLE_CODE = "role_code";

}


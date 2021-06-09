package com.eazitasc.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sys_persistent_login.class)
public abstract class Sys_persistent_login_ {

	public static volatile SingularAttribute<Sys_persistent_login, String> series;
	public static volatile SingularAttribute<Sys_persistent_login, Date> last_used;
	public static volatile SingularAttribute<Sys_persistent_login, String> username;
	public static volatile SingularAttribute<Sys_persistent_login, String> token;

	public static final String SERIES = "series";
	public static final String LAST_USED = "last_used";
	public static final String USERNAME = "username";
	public static final String TOKEN = "token";

}


package com.eazitasc.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mt_employee.class)
public abstract class Mt_employee_ extends com.eazitasc.entity.Auditable_entity_ {

	public static volatile SingularAttribute<Mt_employee, String> full_name;
	public static volatile SingularAttribute<Mt_employee, String> address;
	public static volatile SingularAttribute<Mt_employee, Date> date_of_birth;
	public static volatile SingularAttribute<Mt_employee, String> mobile_phone;
	public static volatile SingularAttribute<Mt_employee, Date> date_of_joining;
	public static volatile SingularAttribute<Mt_employee, String> company_email;
	public static volatile SingularAttribute<Mt_employee, String> short_name;
	public static volatile SingularAttribute<Mt_employee, String> personal_email;
	public static volatile SingularAttribute<Mt_employee, String> employee_code;

	public static final String FULL_NAME = "full_name";
	public static final String ADDRESS = "address";
	public static final String DATE_OF_BIRTH = "date_of_birth";
	public static final String MOBILE_PHONE = "mobile_phone";
	public static final String DATE_OF_JOINING = "date_of_joining";
	public static final String COMPANY_EMAIL = "company_email";
	public static final String SHORT_NAME = "short_name";
	public static final String PERSONAL_EMAIL = "personal_email";
	public static final String EMPLOYEE_CODE = "employee_code";

}


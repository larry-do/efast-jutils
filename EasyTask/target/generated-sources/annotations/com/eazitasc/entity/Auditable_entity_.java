package com.eazitasc.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Auditable_entity.class)
public abstract class Auditable_entity_ {

	public static volatile SingularAttribute<Auditable_entity, Integer> object_version;
	public static volatile SingularAttribute<Auditable_entity, String> last_updated_by;
	public static volatile SingularAttribute<Auditable_entity, Date> last_updated_datetime;
	public static volatile SingularAttribute<Auditable_entity, String> created_by;
	public static volatile SingularAttribute<Auditable_entity, Date> created_datetime;

	public static final String OBJECT_VERSION = "object_version";
	public static final String LAST_UPDATED_BY = "last_updated_by";
	public static final String LAST_UPDATED_DATETIME = "last_updated_datetime";
	public static final String CREATED_BY = "created_by";
	public static final String CREATED_DATETIME = "created_datetime";

}


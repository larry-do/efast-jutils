package com.eazitasc.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

@Transactional
public abstract class AbstractEasyRepo<E, Pk> implements EasyRepoInterface<E, Pk> {

    private final Class<E> entityClazz;

    @PersistenceContext
    protected EntityManager entityManager;

    protected AbstractEasyRepo() {
        //noinspection unchecked
        this.entityClazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public E getByPk(Pk pk) {
        return entityManager.find(entityClazz, pk);
    }
}

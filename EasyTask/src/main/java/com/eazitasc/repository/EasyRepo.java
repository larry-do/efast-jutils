package com.eazitasc.repository;

public interface EasyRepo<E, Pk> {
    void create(E entity);

    void update(E entity);

    void remove(E entity);

    E getByPk(Pk pk);
}

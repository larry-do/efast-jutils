package com.eazitasc.repository;

public interface EasyRepoInterface<E, Pk> {
    void create(E entity);

    void update(E entity);

    void remove(E entity);

    E getByPk(Pk pk);
}

package com.eazitasc.repository;

import com.eazitasc.entity.Mt_user;
import com.eazitasc.entity.Mt_user_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Mt_user mt_user) {
        this.entityManager.persist(mt_user);
    }

    @Override
    public void update(Mt_user mt_user) {
        this.entityManager.merge(mt_user);
    }

    @Override
    public void remove(Mt_user mt_user) {
        this.entityManager.remove(mt_user);
    }

    @Override
    public Mt_user getByPk(Mt_user.Pk pk) {
        return this.entityManager.find(Mt_user.class, pk);
    }

    @Override
    public Mt_user getByPk(String user_code) {
        return this.getByPk(new Mt_user.Pk(user_code));
    }

    @Override
    public Mt_user getByUsername(String username) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_user> cq = cb.createQuery(Mt_user.class);
        Root<Mt_user> root = cq.from(Mt_user.class);
        cq.where(cb.equal(root.get(Mt_user_.username), username));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

    @Override
    public Mt_user getByEmployee_code(String employee_code) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_user> cq = cb.createQuery(Mt_user.class);
        Root<Mt_user> root = cq.from(Mt_user.class);
        cq.where(cb.equal(root.get(Mt_user_.employee_code), employee_code));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

}
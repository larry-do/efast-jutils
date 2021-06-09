package com.eazitasc.repository;

import com.eazitasc.entity.Mt_role;
import com.eazitasc.entity.Mt_role_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleRepositoryImpl extends AbstractEasyRepo<Mt_role, Mt_role.Pk> implements RoleRepository {

    @Override
    public Mt_role getByPk(String role_code) {
        return this.getByPk(new Mt_role.Pk(role_code));
    }

    @Override
    public Mt_role getByRole_name(String role_name) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_role> cq = cb.createQuery(Mt_role.class);
        Root<Mt_role> root = cq.from(Mt_role.class);
        cq.where(cb.and(cb.equal(root.get(Mt_role_.role_name), role_name)));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

}
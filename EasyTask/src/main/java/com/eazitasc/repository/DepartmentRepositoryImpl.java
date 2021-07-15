package com.eazitasc.repository;

import com.eazitasc.entity.Mt_department;
import com.eazitasc.entity.Mt_department_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DepartmentRepositoryImpl extends AbstractEasyRepo<Mt_department, Mt_department.Pk> implements DepartmentRepository {

    @Override
    public Mt_department getByPk(String department_code) {
        return this.getByPk(new Mt_department.Pk(department_code));
    }

    @Override
    public Mt_department getByDepartment_name(String department_name) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_department> cq = cb.createQuery(Mt_department.class);
        Root<Mt_department> root = cq.from(Mt_department.class);
        cq.where(cb.and(cb.equal(root.get(Mt_department_.department_name), department_name)));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

    @Override
    public Mt_department getByAbbreviationn(String abbreviationn) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_department> cq = cb.createQuery(Mt_department.class);
        Root<Mt_department> root = cq.from(Mt_department.class);
        cq.where(cb.and(cb.equal(root.get(Mt_department_.abbreviationn), abbreviationn)));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

}
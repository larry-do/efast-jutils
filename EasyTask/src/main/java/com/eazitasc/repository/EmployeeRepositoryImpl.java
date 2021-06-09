package com.eazitasc.repository;

import com.eazitasc.entity.Mt_employee;
import com.eazitasc.entity.Mt_employee_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmployeeRepositoryImpl extends AbstractEasyRepo<Mt_employee, Mt_employee.Pk> implements EmployeeRepository {

    @Override
    public Mt_employee getByPk(String employee_code) {
        return this.getByPk(new Mt_employee.Pk(employee_code));
    }

    @Override
    public Mt_employee getByShort_name(String short_name) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_employee> cq = cb.createQuery(Mt_employee.class);
        Root<Mt_employee> root = cq.from(Mt_employee.class);
        cq.where(cb.and(cb.equal(root.get(Mt_employee_.short_name), short_name)));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

    @Override
    public Mt_employee getByCompany_email(String company_email) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_employee> cq = cb.createQuery(Mt_employee.class);
        Root<Mt_employee> root = cq.from(Mt_employee.class);
        cq.where(cb.and(cb.equal(root.get(Mt_employee_.company_email), company_email)));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

    @Override
    public Mt_employee getByPersonal_email(String personal_email) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_employee> cq = cb.createQuery(Mt_employee.class);
        Root<Mt_employee> root = cq.from(Mt_employee.class);
        cq.where(cb.and(cb.equal(root.get(Mt_employee_.personal_email), personal_email)));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

    @Override
    public Mt_employee getByMobile_phone(String mobile_phone) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Mt_employee> cq = cb.createQuery(Mt_employee.class);
        Root<Mt_employee> root = cq.from(Mt_employee.class);
        cq.where(cb.and(cb.equal(root.get(Mt_employee_.mobile_phone), mobile_phone)));
        return this.entityManager.createQuery(cq.select(root)).getSingleResult();
    }

}
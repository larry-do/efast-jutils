package com.eazitasc.repository;

import com.eazitasc.entity.Mt_employee;

public interface EmployeeRepository extends EasyRepoInterface<Mt_employee, Mt_employee.Pk> {
    Mt_employee getByPk(String employee_code);

    Mt_employee getByShort_name(String short_name);

    Mt_employee getByCompany_email(String company_email);

    Mt_employee getByPersonal_email(String personal_email);

    Mt_employee getByMobile_phone(String mobile_phone);
}
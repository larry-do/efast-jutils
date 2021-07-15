package com.eazitasc.repository;

import com.eazitasc.entity.Mt_department;

public interface DepartmentRepository extends EasyRepoInterface<Mt_department, Mt_department.Pk> {
    Mt_department getByPk(String department_code);

    Mt_department getByDepartment_name(String department_name);

    Mt_department getByAbbreviationn(String abbreviationn);
}
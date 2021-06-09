package com.eazitasc.repository;

import com.eazitasc.entity.Mt_user;

public interface UserRepository extends EasyRepoInterface<Mt_user, Mt_user.Pk> {
    Mt_user getByPk(String user_code);

    Mt_user getByUsername(String username);

    Mt_user getByEmployee_code(String employee_code);
}
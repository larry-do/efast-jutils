package com.eazitasc.repository;

import com.eazitasc.entity.Mt_user;

public interface UserRepository {
    void create(Mt_user mt_user);

    void update(Mt_user mt_user);

    void remove(Mt_user mt_user);

    Mt_user getByPk(Mt_user.Pk pk);

    Mt_user getByPk(String user_code);

    Mt_user getByUsername(String username);

    Mt_user getByEmployee_code(String employee_code);
}

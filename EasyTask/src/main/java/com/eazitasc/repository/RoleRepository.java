package com.eazitasc.repository;

import com.eazitasc.entity.Mt_role;

public interface RoleRepository extends EasyRepoInterface<Mt_role, Mt_role.Pk> {
    Mt_role getByPk(String role_code);

    Mt_role getByRole_name(String role_name);
}
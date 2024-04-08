package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.domain.enums.RoleEnum;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<Role> findAll();

    Role findByRoleEnumType(RoleEnum roleEnum);

}

package com.tokioschool.filmotokio.service.impl;


import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.domain.enums.RoleEnum;
import com.tokioschool.filmotokio.repository.RoleRepository;
import com.tokioschool.filmotokio.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;

    }

    @Override
    public Set<Role> findAll() {

        List<Role> roleList = (List<Role>) roleRepository.findAll();
        return new HashSet<>(roleList);
    }

    @Override
    public Role findByRoleEnumType(RoleEnum roleEnum) {

        return roleRepository.findByRole(roleEnum);
    }
}

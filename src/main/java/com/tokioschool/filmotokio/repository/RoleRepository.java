package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.domain.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Query("SELECT r FROM Role r WHERE r.name = :roleEnum")
//    Role findByName(@Param("roleEnum") RoleEnum roleEnum);

    Role findByRole(RoleEnum roleEnum);



}

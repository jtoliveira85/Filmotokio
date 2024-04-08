package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.dto.CreateUserDTO;
import com.tokioschool.filmotokio.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    boolean save(CreateUserDTO newUserDTO);
    boolean update(UserDTO userDTO);


    UserDTO findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean updateLoginDate(User user);

}

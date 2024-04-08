package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.domain.enums.RoleEnum;
import com.tokioschool.filmotokio.dto.CreateUserDTO;
import com.tokioschool.filmotokio.dto.UserDTO;
import com.tokioschool.filmotokio.exception.RequiredObjectsIsNullException;
import com.tokioschool.filmotokio.exception.ResourceNotFoundException;
import com.tokioschool.filmotokio.mapper.Mapper;
import com.tokioschool.filmotokio.repository.RoleRepository;
import com.tokioschool.filmotokio.repository.UserRepository;
import com.tokioschool.filmotokio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AuthService authService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    @Override
    public List<User> findAll() {
        logger.info("UserService: Finding All Users");
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        logger.info("UserService: Finding User by id");
        Optional<User> result = userRepository.findById(id);
        User user = null;
        if (result.isPresent()) {
            user = result.get();
        } else {
            logger.warn("UserService: User id {}, not found", id);
            throw new RuntimeException("User com id " + id + ", não encontrado");
        }

        return user;
    }

    @Override
    @Transactional
    public boolean save(CreateUserDTO newUserDTO) {


        if (newUserDTO == null) {
            logger.warn("UserService -> Save: NULL USER");
            //TODO Lanção exeção
            throw new RequiredObjectsIsNullException();
        }

        var entity = Mapper.parseObject(newUserDTO, User.class);

        if (entity != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            entity.setPassword(bCryptPasswordEncoder.encode(newUserDTO.getPassword()));
            entity.setCreationDate(LocalDateTime.now());
            entity.setActive(true);
            entity.setImage("/user-profile.png");

            if (entity.getRoles().isEmpty()) {
                /////////////////////////////////////////////////////////
                Role role = roleRepository.findByRole(RoleEnum.ROLE_USER);
                entity.getRoles().add(role);
            }
            userRepository.save(entity);
            logger.info("UserService: User {} Saved!", entity.getUsername());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(UserDTO userDTO) {

        if (userDTO == null) {
            logger.warn("UserService -> Save: NULL USER");
            throw new RequiredObjectsIsNullException();
        }

        User currUser = Mapper.parseObject(userRepository.findByUsername(userDTO.getUsername()), User.class);

        currUser.setName(userDTO.getName());
        currUser.setSurname(userDTO.getSurname());
        currUser.setEmail(userDTO.getEmail());
        currUser.setBirthDate(userDTO.getBirthDate());

        if (userDTO.getImage() != null) {
            currUser.setImage(userDTO.getImage());
            authService.refreshAuthUser(userDTO.getImage());
        }

        userRepository.save(currUser);


        return true;
    }


    @Override
    public UserDTO findByUsername(String username) {
        logger.info("UserService: finding user by username: " + username);

        return Mapper.parseObject(userRepository.findByUsername(username), UserDTO.class);
    }

    @Override
    public boolean existsByUsername(String username) {
        logger.info("UserService: check if username exists: " + username);
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        logger.info("UserService: check if email exists: " + email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean updateLoginDate(User user) {

        Optional<User> result = userRepository.findById(user.getId());

        User userToUpdate = null;

        if (result.isPresent()) {
            userToUpdate = result.get();
        }
        else {
            throw new ResourceNotFoundException("User não Encontrado");
        }

        try {
            userToUpdate.setLastLogin(LocalDateTime.now());
        } catch (RuntimeException exception) {
            throw new RuntimeException("Erro ao Gravar");
        }

        userToUpdate.setLastLogin(LocalDateTime.now());
        userRepository.save(userToUpdate);

        return true;
    }
}

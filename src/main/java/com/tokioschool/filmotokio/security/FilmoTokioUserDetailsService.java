package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.repository.UserRepository;
import com.tokioschool.filmotokio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmoTokioUserDetailsService implements UserDetailsService {


    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public FilmoTokioUserDetailsService(UserService userService,
                                        UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username ou Password inválidos");
        }

        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getRole().toString())));
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new FilmoTokioUserDetails(user.getUsername(), user.getPassword(),
                user.getActive(), true, true, true, authorities, user.getId(), user.getImage());
    }


}

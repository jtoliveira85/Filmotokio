package com.tokioschool.filmotokio.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class FilmoTokioUserDetails extends User {

    private final Long userId;

    private String userImage;


    public FilmoTokioUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, String userImage) {
        super(username, password, authorities);
        this.userId = userId;
        this.userImage = userImage;
    }

    public FilmoTokioUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long userId, String userImage) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.userImage = userImage;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString() {
        return "TokioUserDetails{" +
                "userId=" + userId +
                ", userImage='" + userImage + '\'' +
                "} " + super.toString();
    }
}

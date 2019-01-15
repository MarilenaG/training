package com.training.fullstack.users.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class UserPrincipal  implements UserDetails {
    private String userName;
    private String password;
    private List<GrantedAuthority> roles ;
    private User user;  // set when a user is authenticated
    private UserType userType ;// user, admin or mentor;

    public UserPrincipal() {
    }

    public UserPrincipal(User user) {
        this.userName = user.getUserName();
        this.roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        this.password=user.getPassword();
        this.user =user;
        this.userType = UserType.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return user!=null?user.getActive():true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user!=null?user.getActive():true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user!=null?user.getActive():true;
    }

    @Override
    public boolean isEnabled() {
        return user!=null?user.getActive():true;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType){
        this.userType = UserType.valueOf(userType);
    }
}

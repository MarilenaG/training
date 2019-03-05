package com.training.fullstack.auth;

import com.training.fullstack.users.model.ApplicationMember;
import com.training.fullstack.users.model.UserType;
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
    private boolean active;
    private UserType userType ;// user, admin or mentor;

    public UserPrincipal() {
    }

    public UserPrincipal(ApplicationMember applicationMember) {
        this.userName = applicationMember.getUserName();
        this.password=applicationMember.getPassword();
        this.userType = applicationMember.getUserType();
        this.active = applicationMember.getActive();
        this.roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userType.name()));

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
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
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

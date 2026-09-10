package com.rideflow.user.userdetails;

import com.rideflow.user.entity.Permission;
import com.rideflow.user.entity.Role;
import com.rideflow.user.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrincipal implements UserDetails {

    private final UUID id;
    private final String username;
    private final String password;
    private final boolean active;
    private final Set<GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.username = user.getMobileNumber() != null ? user.getMobileNumber() : user.getEmail();
        this.password = user.getPassword();
        this.active = user.isActive();
        Set<GrantedAuthority> granted = new HashSet<>();
        for (Role role : user.getRoles()) {
            granted.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
            for(Permission permission : role.getPermissions()) {
                granted.add(new SimpleGrantedAuthority("PERM_" + permission.getName().name()));
            }
        }
        this.authorities = granted;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

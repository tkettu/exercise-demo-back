package com.terok.demo.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terok.demo.models.Role;
import com.terok.demo.models.Users;

public class UserPrincipal implements UserDetails {

	private ObjectId id;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;
	
	public Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal(ObjectId id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
	
	public static UserPrincipal create(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> 
        										new SimpleGrantedAuthority(Role.ROLE_USER.toString())
        										).collect(Collectors.toList());
        	//	user.getRoles().stream().map(role ->
               // new SimpleGrantedAuthority(role.getName().name())
       // ).collect(Collectors.toList());

		
        return new UserPrincipal(
                user.get_id(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

	public ObjectId getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

}

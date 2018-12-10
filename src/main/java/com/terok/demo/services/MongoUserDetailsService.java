package com.terok.demo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.terok.demo.models.Users;
import com.terok.demo.repositories.UsersRepository;

public class MongoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repository.findByUserName(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
	    //return (UserDetails) new UserUser(user.getUsername(), user.getPassword(), authorities);
	    return new User(user.getUserName(), user.getPassword(), authorities);
	}

}

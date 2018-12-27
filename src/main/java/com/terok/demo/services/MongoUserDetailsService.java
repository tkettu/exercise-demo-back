package com.terok.demo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.terok.demo.models.Users;
import com.terok.demo.repositories.UsersRepository;
import com.terok.demo.security.UserPrincipal;

@Component
public class MongoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository repository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return UserPrincipal.create(user);
		// List<SimpleGrantedAuthority> authorities = Arrays.asList(new
		// SimpleGrantedAuthority("user"));
		// return (UserDetails) new UserUser(user.getUsername(), user.getPassword(),
		// authorities);
		// return new User(user.getUserName(), user.getPassword(), authorities);
	}

	@Transactional
    public UserDetails loadUserById(ObjectId _id) {
	 	
		Optional<Users> user = repository.findById(_id);
		//Users user = repository.findById(_id);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
	
		return UserPrincipal.create(user.get());
	 	

        //.orElseThrow(
        //    () -> new UserNameNotFound("User not found with id " + id)
        //);

    }
	
	

}

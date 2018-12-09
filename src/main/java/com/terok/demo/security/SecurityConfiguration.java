package com.terok.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	//@Autowired
	//MongoUserDetailsService userDetailsService;
	
	//Logger logger
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable().cors()//.and()
//			.authorizeRequests()
//				.antMatchers("/", "home").permitAll()
//				.anyRequest().authenticated()
//			.and()
//			.formLogin()
//				.loginPage("/api/login")
//				.permitAll()
//			.and()
//			.logout()
//				.permitAll()
				
				
			;
			
		//super.configure(http);
	}
	
}

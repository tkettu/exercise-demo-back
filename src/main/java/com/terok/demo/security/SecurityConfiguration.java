package com.terok.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;

import com.terok.demo.services.MongoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
//@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	MongoUserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// .csrf().disable().cors().and()
				.cors().and().csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.and()
				.sessionManagement()//.disable()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api/login").permitAll()
				.antMatchers("/api/user/registration").permitAll()
				//.antMatchers("/api/exercises").permitAll()  //TEST
				.anyRequest().authenticated() //TEST
				 //.and().httpBasic()
				//.and().sessionManagement().disable();
				;
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

//@Configuration
//@EnableConfigurationProperties
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	MongoUserDetailsService userDetailsService;
//	
//	//Logger logger
//	 @Override
//	  protected void configure(HttpSecurity http) throws Exception {
//	    http
//	      .csrf().disable()
//	      .authorizeRequests().anyRequest().authenticated()
//	      .and().httpBasic()
//	      .and().sessionManagement().disable();
//	  }
//	 
////	@Override
////	protected void configure(HttpSecurity http) throws Exception {
////		
////		http
////			.csrf().disable().cors().
////			and().
////			authorizeRequests()
////			.antMatchers("/api/login").permitAll()
////			
////			.anyRequest().authenticated()
////			//.and().httpBasic()
////			.and().sessionManagement().disable()
//////				.antMatchers("/", "home").permitAll()
//////				.anyRequest().authenticated()
//////			.and()
//////			.formLogin()
//////				.loginPage("/api/login")
//////				.permitAll()
//////			.and()
//////			.logout()
//////				.permitAll()
//				
//			;
//			
//		//super.configure(http);
////	}
//	
////	@Override
////	public void configure(AuthenticationManagerBuilder builder) throws Exception {
////		builder.
////		
////	}
//	
////	@Override
////	public void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth.userDetailsService(userDetailsService);
////		
////	}
//	
//	@Override
//	public void configure(AuthenticationManagerBuilder builder) throws Exception {
//		builder.userDetailsService(userDetailsService);
//	}		
//			
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(4);
//	}
//	
//}

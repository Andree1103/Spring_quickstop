package com.cibertec.saludo;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/*http.authorizeHttpRequests((auth)-> auth.anyRequest().authenticated())
			.formLogin(form-> form.loginPage("/login")
			.permitAll().defaultSuccessUrl("/Producto/lista"));*/
		http.csrf(csrf->csrf.disable())		
		.authorizeHttpRequests((auth)-> 
			auth.anyRequest()
				.authenticated()
			)
		.formLogin((form)-> form
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/Inicio",true)
			);			
		return http.build();

	}
	
	
	 /*@Bean
	 public UserDetailsService userDetailsService(BCryptPasswordEncoder encoder) {
		 InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		 manager.createUser(User.withUsername("henry").password(encoder.encode("123")).roles("USER").build());
		 manager.createUser(User.withUsername("andree").password(encoder.encode("123")).roles("ADMINISTRADOR").build());
		 return manager;
	 }*/
	 
}

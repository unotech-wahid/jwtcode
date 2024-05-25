package com.lens.security.authentication.config;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lens.security.authentication.CustomUserDetails;
import com.lens.security.authentication.entity.User;
import com.lens.security.authentication.repository.UserRepository;
import com.lens.security.authentication.service.RoleService;

@Configuration
public class ApplicationConfig {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			User user = userRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			// Fetch roles and authorities based on user's department
			Set<GrantedAuthority> authorities = roleService.getRolesAndAuthoritiesByDepartments(user.getDepartments())
					.stream().flatMap(role -> role.getAuthorities().stream())
					.map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toSet());

			// Add roles as authorities
			authorities.addAll(roleService.getRolesAndAuthoritiesByDepartments(user.getDepartments()).stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toSet()));

			return new CustomUserDetails(user, authorities);
		};
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}

package com.lens.security.authentication.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lens.security.authentication.CustomUserDetails;
import com.lens.security.authentication.config.JwtService;
import com.lens.security.authentication.entity.AuthenticationRequest;
import com.lens.security.authentication.entity.AuthenticationResponse;
import com.lens.security.authentication.entity.Authority;
import com.lens.security.authentication.entity.Department;
import com.lens.security.authentication.entity.Role;
import com.lens.security.authentication.entity.User;
import com.lens.security.authentication.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RoleService roleService;

	public String register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User created successfully";
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			User user = userRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			CustomUserDetails userDetails = new CustomUserDetails(user, getAuthorities(user));
			String jwtToken = jwtService.generateToken(userDetails);
			return AuthenticationResponse.builder().accessToken(jwtToken).build();
		} catch (UsernameNotFoundException e) {
			return AuthenticationResponse.builder().errorMessage("User not found").build();
		}
	}

	private Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		try {
			if (user == null) {
				System.out.println("User object is null.");
				return authorities;
			}
			Set<Department> departments = user.getDepartments();
			if (departments != null) {
				Set<Role> rolesAndAuthorities = roleService.getRolesAndAuthoritiesByDepartments(departments);
				for (Role role : rolesAndAuthorities) {
					authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
					for (Authority authority : role.getAuthorities()) {
						authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
					}
				}
			} else {
				System.out.println("Departments are null for user: " + user.getName());
			}
		} catch (Exception e) {
			System.out.println("An error occurred while fetching authorities: " + e.getMessage());
		}
		return authorities;
	}
}

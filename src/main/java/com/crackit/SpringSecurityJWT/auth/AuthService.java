package com.crackit.SpringSecurityJWT.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crackit.SpringSecurityJWT.config.JwtService;
import com.crackit.SpringSecurityJWT.user.Authority;
import com.crackit.SpringSecurityJWT.user.Department;
import com.crackit.SpringSecurityJWT.user.Role;
import com.crackit.SpringSecurityJWT.user.User;
import com.crackit.SpringSecurityJWT.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User created successfully";
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    	try {
        	
   authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
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
      
            Set<Department> departments = user.getDepartments();
            if (departments != null) {
                Set<Role> rolesAndAuthorities = roleService.getRolesAndAuthoritiesByDepartments(departments);
                for (Role role : rolesAndAuthorities) {
                    System.out.println("==========================role===================================" + role.getName());
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                    for (Authority authority : role.getAuthorities()) {
                        System.out.println("========================authority  for loop=========================" + authority);
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

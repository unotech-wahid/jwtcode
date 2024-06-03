package com.lens.security.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lens.security.authentication.entity.AuthenticationRequest;
import com.lens.security.authentication.entity.AuthenticationResponse;
import com.lens.security.authentication.entity.Authority;
import com.lens.security.authentication.entity.Role;
import com.lens.security.authentication.entity.User;
import com.lens.security.authentication.repository.AuthorityRepository;
import com.lens.security.authentication.repository.RoleRepository;
import com.lens.security.authentication.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private RoleRepository roleRepository;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) throws Exception {
		String authResponse = authService.register(user);
		return ResponseEntity.ok(authResponse);
	}

	@PostMapping("/saveRole")
	public ResponseEntity<?> register(@RequestBody Role role) {
		for (Authority authority : role.getAuthorities()) {
			authorityRepository.save(authority);
		}

		Role savedRole = roleRepository.save(role);
		return ResponseEntity.ok(savedRole);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

}

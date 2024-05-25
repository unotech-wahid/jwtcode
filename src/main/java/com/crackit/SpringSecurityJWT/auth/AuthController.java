package com.crackit.SpringSecurityJWT.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.crackit.SpringSecurityJWT.user.Authority;
import com.crackit.SpringSecurityJWT.user.Role;
import com.crackit.SpringSecurityJWT.user.User;
import com.crackit.SpringSecurityJWT.user.repository.AuthorityRepository;
import com.crackit.SpringSecurityJWT.user.repository.RoleRepository;
import com.crackit.SpringSecurityJWT.user.repository.UserRepository;

@RestController("/crackit/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private RoleRepository roleRepository;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		String authResponse = authService.register(user);
		return ResponseEntity.ok(authResponse);
	}

	@PostMapping("/saveRole")
	public ResponseEntity<?> register(@RequestBody Role role) {
		// Save all authorities associated with the role
		for (Authority authority : role.getAuthorities()) {
			authorityRepository.save(authority);
		}
		// Now save the role
		Role savedRole = roleRepository.save(role);
		return ResponseEntity.ok(savedRole);
	}

	@PostMapping("/saveUser")
	public ResponseEntity<?> saveuser(@RequestBody User user) {
		User user2 = userRepository.save(user);
		return ResponseEntity.ok(user2);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

}

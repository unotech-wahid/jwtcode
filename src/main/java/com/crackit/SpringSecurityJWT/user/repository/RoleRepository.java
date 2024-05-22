package com.crackit.SpringSecurityJWT.user.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crackit.SpringSecurityJWT.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	
	    Set<Role> findByName(String departmentNames);

}

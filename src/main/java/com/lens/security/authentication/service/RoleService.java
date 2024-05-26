package com.lens.security.authentication.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lens.security.authentication.entity.Department;
import com.lens.security.authentication.entity.Role;
import com.lens.security.authentication.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Set<Role> getRolesAndAuthoritiesByDepartments(Set<Department> departments) {
		Set<Role> rolesAndAuthorities = new HashSet<>();
		for (Department department : departments) {
			String departmentName = department.getName();
			Set<Role> roles = roleRepository.findByName(departmentName);
			if (roles != null) {
				rolesAndAuthorities.addAll(roles);
			}
		}
		return rolesAndAuthorities;
	}
}

package com.crackit.SpringSecurityJWT.auth;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crackit.SpringSecurityJWT.user.Department;
import com.crackit.SpringSecurityJWT.user.Role;
import com.crackit.SpringSecurityJWT.user.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRolesAndAuthoritiesByDepartments(Set<Department> departments) {
    	System.out.println("===========getRolesAndAuthoritiesByDepartments==================");
        Set<Role> rolesAndAuthorities = new HashSet<>();
        for (Department department : departments) {
            String departmentName = department.getName();
            System.out.println(departmentName+"=============departmentName=================");
            Set<Role> roles = roleRepository.findByName(departmentName);
            System.out.println("=====================roles============"+roles);
            if (roles != null) {
                rolesAndAuthorities.addAll(roles);
            }
        }
        return rolesAndAuthorities;
    }
}

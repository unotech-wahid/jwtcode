package com.crackit.SpringSecurityJWT.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crackit.SpringSecurityJWT.user.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}

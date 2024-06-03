package com.lens.security.authentication.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lens.security.authentication.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

	public Set<Department> findBynameIn(Set<String> departmentName);
}

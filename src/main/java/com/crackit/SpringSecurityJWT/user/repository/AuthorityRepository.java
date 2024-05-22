package com.crackit.SpringSecurityJWT.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crackit.SpringSecurityJWT.user.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}

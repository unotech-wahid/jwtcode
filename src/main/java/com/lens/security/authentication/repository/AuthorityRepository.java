package com.lens.security.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lens.security.authentication.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}


package com.spring.usermanagement.repository;

import com.spring.usermanagement.entity.Role;
import com.spring.usermanagement.entity.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(ERole name);

}

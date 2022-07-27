package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(ERole name);

}

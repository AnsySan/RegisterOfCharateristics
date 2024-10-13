package com.ansysan.register_of_characteristics.repository;

import com.ansysan.register_of_characteristics.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByName(String name);
}

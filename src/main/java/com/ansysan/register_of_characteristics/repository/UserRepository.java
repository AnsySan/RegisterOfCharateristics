package com.ansysan.register_of_characteristics.repository;

import com.ansysan.register_of_characteristics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

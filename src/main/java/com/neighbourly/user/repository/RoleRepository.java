package com.neighbourly.user.repository;

import com.neighbourly.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find a role by its name:
    // Optional<Role> findByName(String name);
}

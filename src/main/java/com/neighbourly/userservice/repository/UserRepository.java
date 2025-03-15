package com.neighbourly.userservice.repository;

import com.neighbourly.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}

package com.neighbourly.userservice.entity;

import com.neighbourly.userservice.constants.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    private Long userId;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String community;
    private String flatNo;
    private String remarks;
    private boolean active;
    private Set<Role> roles;
    @CreatedDate
    private LocalDateTime createdOnTime;
    private LocalDateTime lastLoginTime;
    @LastModifiedDate
    private LocalDateTime lastUpdatedTime;
}

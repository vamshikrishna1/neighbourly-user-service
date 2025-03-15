package com.neighbourly.userservice.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String community;
    private String flatNo;
    private String remarks;
    private boolean active;
    private LocalDateTime createdOnTime;
    private LocalDateTime lastLoginTime;
    private LocalDateTime lastUpdatedTime;

}

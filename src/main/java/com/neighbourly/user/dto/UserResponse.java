package com.neighbourly.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String requestIdentifier;
    private UserDto userDto;
}

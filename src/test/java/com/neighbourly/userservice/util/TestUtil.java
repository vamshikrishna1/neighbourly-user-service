package com.neighbourly.userservice.util;

import com.neighbourly.userservice.dto.UserDto;

import java.time.LocalDateTime;

public class TestUtil {

    public static UserDto createTestUser(){
       UserDto userDto =new UserDto();
       userDto.setFirstName("Vamshi Krishna");
       userDto.setLastName("Yerramalla");
       userDto.setPassword("password");
       userDto.setActive(true);
       userDto.setCreatedOnTime(LocalDateTime.now());
       userDto.setEmail("vamshiyerramalla@gmail.com");
       userDto.setCommunity("Marina Skies");
       userDto.setPhoneNumber("9700477143");
       return userDto;
    }

}

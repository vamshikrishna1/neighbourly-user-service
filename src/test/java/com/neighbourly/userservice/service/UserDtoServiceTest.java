package com.neighbourly.userservice.service;

import com.neighbourly.userservice.dto.UserDto;
import com.neighbourly.userservice.util.TestUtil;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class UserDtoServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser_test(){
        UserDto userDto = TestUtil.createTestUser();
        UserDto createdUserDto = userService.createUser(userDto);
        assertThat(createdUserDto,CoreMatchers.notNullValue());
    }

}

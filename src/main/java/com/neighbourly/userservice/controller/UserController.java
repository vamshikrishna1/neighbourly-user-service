package com.neighbourly.userservice.controller;

import com.neighbourly.userservice.dto.UserDto;
import com.neighbourly.userservice.dto.UserResponse;
import com.neighbourly.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserResponse> create(@RequestHeader String uuid, @RequestBody UserDto userDto){

        UserDto createdUserDto = userService.createUser(userDto);
        UserResponse userResponse = UserResponse.builder()
                .requestIdentifier(uuid)
                .userDto(createdUserDto)
                .build();
        return ResponseEntity.ok(userResponse);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> get(@RequestHeader String uuid,@PathVariable Long userId){
        UserDto userDto = userService.getUser(userId);
        UserResponse userResponse = UserResponse.builder().userDto(userDto).requestIdentifier(uuid).build();
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity update(@RequestHeader String uuid, @PathVariable String userId,
                                  @RequestBody UserDto userDto){
       UserDto updatedUserDto = userService.update(userDto);
        UserResponse userResponse = UserResponse.builder().userDto(updatedUserDto).requestIdentifier(uuid).build();
        return ResponseEntity.ok(userResponse);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@RequestHeader String uuid, @PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}

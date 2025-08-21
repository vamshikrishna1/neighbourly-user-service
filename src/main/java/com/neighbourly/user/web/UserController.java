package com.neighbourly.user.web;

import com.neighbourly.user.dto.HeaderInfo;
import com.neighbourly.user.dto.Response;
import com.neighbourly.user.dto.RoleDto;
import com.neighbourly.user.dto.UserDto;
import com.neighbourly.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Response<UserDto>> create(HeaderInfo headers, @Validated @RequestBody UserDto userDto) {
        Response<UserDto> response = userService.createUser(userDto, headers);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> getAll(HeaderInfo headers, @RequestParam(required = false) String email) {
        Response<List<UserDto>> response = userService.getAllUsers(headers, email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Response<UserDto>> get(HeaderInfo headers, @PathVariable Long userId) {
        Response<UserDto> response = userService.getUser(userId, headers);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Response<UserDto>> update(HeaderInfo headers, @PathVariable Long userId,
                                  @RequestBody UserDto userDto){
        Response<UserDto> response = userService.update(userId, userDto);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(HeaderInfo headers, @PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<Response<UserDto>> addRoleToUser(HeaderInfo headers, @PathVariable Long userId,
                                                            @PathVariable Long roleId) {
        Response<UserDto> response = userService.addRoleToUser(userId, roleId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/roles")
    public ResponseEntity<Response<UserDto>> updateUserRoles(HeaderInfo headers, @PathVariable Long userId,
                                                              @RequestBody List<RoleDto> roleDtoList) {
        Response<UserDto> response = userService.updateUserRoles(userId, roleDtoList, headers);
        return ResponseEntity.ok(response);
    }




}

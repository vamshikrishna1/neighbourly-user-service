package com.neighbourly.user.service;


import com.neighbourly.user.dto.HeaderInfo;
import com.neighbourly.user.dto.Response;
import com.neighbourly.user.dto.RoleDto;
import com.neighbourly.user.dto.UserDto;
import com.neighbourly.user.entity.Role;
import com.neighbourly.user.entity.User;
import com.neighbourly.user.exception.UserAlreadyExistsException;
import com.neighbourly.user.exception.UserNotFoundException;
import com.neighbourly.user.mapper.RoleMapper;
import com.neighbourly.user.mapper.UserMapper;
import com.neighbourly.user.repository.RoleRepository;
import com.neighbourly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public Response<UserDto> createUser(UserDto userDto, HeaderInfo headers) {

        Optional<User> existingUserOpt = userRepository.findByEmail(userDto.getEmail());
        if (existingUserOpt.isPresent()) {
            throw new UserAlreadyExistsException(MessageFormat.format("User with email: {0} already exists", userDto.getEmail()));
        }

        var userEntity = userMapper.toEntity(userDto);
        User createdUserEntity = userRepository.save(userEntity);
        UserDto createdUserDto = userMapper.toDto(createdUserEntity);
        return Response.<UserDto>builder()
                .data(createdUserDto)
                .uuid(headers.getUuid())
                .build();
    }


    public Response<UserDto> getUser(Long userId, HeaderInfo headers) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with id: {0} not found", userId)));
        UserDto userDto = userMapper.toDto(user);
        return Response.<UserDto>builder()
                .data(userDto)
                .uuid(headers.getUuid())
                .build();
    }


    public void delete(Long userId){
        userRepository.deleteById(userId);
    }


    @Transactional
    public Response<UserDto> update(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        User updatedUserDto = userMapper.toEntity(userDto, existingUser);
        return Response.<UserDto>builder()
                .data(userMapper.toDto(updatedUserDto))
                .build();

    }

    public Response<List<UserDto>> getAllUsers(HeaderInfo headers, String email) {

        if (!isEmpty(email)) {
            Optional<User> userOpt = userRepository.findByEmail(email);
            User user = userOpt.orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with email: {0} not found", email)));
            UserDto userDto = userMapper.toDto(user);
            return Response.<List<UserDto>>builder()
                    .data(List.of(userDto))
                    .uuid(headers.getUuid())
                    .build();
        }

        var userList = userRepository.findAll();
        var userDtoList = userMapper.toDto(userList);
        return Response.<List<UserDto>>builder()
                .data(userDtoList)
                .uuid(headers.getUuid())
                .build();
    }






    @Transactional
    public Response<UserDto> updateUserRoles(Long userId, List<RoleDto> roleDtos, HeaderInfo headers) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with id: {0} not found", userId)));

        Set<Long> roleIds = roleDtos.stream().map(RoleDto::getId).collect(Collectors.toSet());
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        user.setRoles(roles);

        return Response.<UserDto>builder()
                .data(userMapper.toDto(user))
                .uuid(headers.getUuid())
                .build();
    }

    public Response<UserDto> addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with id: {0} not found", userId)));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new UserNotFoundException(MessageFormat.format("Role with id: {0} not found", roleId)));

        user.getRoles().add(role);
        userRepository.save(user);

        return Response.<UserDto>builder()
                .data(userMapper.toDto(user))
                .build();
    }
}

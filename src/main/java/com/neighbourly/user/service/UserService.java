package com.neighbourly.user.service;


import com.neighbourly.user.constants.SUBSCRIPTON_STATUS;
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

    private final SubscriptionRoleService subscriptionRoleService;
    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public Response<UserDto> createUser(UserDto userDto, HeaderInfo headers) {

        Optional<User> existingUserOpt = userRepository.findByEmail(userDto.getEmail());
        if (existingUserOpt.isPresent()) {
            throw new UserAlreadyExistsException(MessageFormat.format("User with email: {0} already exists", userDto.getEmail()));
        }

        var userEntity = userMapper.toEntity(userDto);
        User createdUserEntity = userRepository.save(userEntity);

        if(userDto.isSubscribed()){
            subscriptionService.assignBasicSubscriptionToUser(createdUserEntity, headers);
        }

        Set<String> roles = fetchUserRoles(createdUserEntity);

        UserDto createdUserDto = userMapper.toDtoWithRoles(createdUserEntity, roles);
        return Response.<UserDto>builder()
                .data(createdUserDto)
                .uuid(headers.getUuid())
                .build();
    }


    public Response<UserDto> getUser(Long userId, HeaderInfo headers) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with id: {0} not found", userId)));
        Set<String> roles = fetchUserRoles(user);
        UserDto userDto = userMapper.toDtoWithRoles(user,roles);
        return Response.<UserDto>builder()
                .data(userDto)
                .uuid(headers.getUuid())
                .build();
    }

    private Set<String> fetchUserRoles(User user) {
        Set<Long> subscriptionIds = fetchSubscriptionIds(user);
        return subscriptionRoleService.getRoleNamesBySubscriptionIds(subscriptionIds);
    }

    private static Set<Long> fetchSubscriptionIds(User user) {
        return user.getUserSubscriptions().stream()
                .filter(us -> us.getStatus() == SUBSCRIPTON_STATUS.ACTIVE)
                .filter(us -> us.getEndDate().isAfter(java.time.LocalDateTime.now()))
                .map(us -> us.getSubscription().getId())
                .collect(Collectors.toSet());
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
            List<UserDto> userDtoList = userOpt.map(userMapper::toDto).map(List::of).orElse(List.of());

            return Response.<List<UserDto>>builder()
                    .data( userDtoList)
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
    public void assignSubscriptionToUser(Long userId, Long subscriptionId, HeaderInfo headers) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        subscriptionService.assignSubscriptionToUser(user, subscriptionId, headers);
    }
}

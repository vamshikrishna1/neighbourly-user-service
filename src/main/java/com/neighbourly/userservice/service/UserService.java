package com.neighbourly.userservice.service;

import com.neighbourly.userservice.dto.UserDto;
import com.neighbourly.userservice.entity.User;
import com.neighbourly.userservice.exception.UserNotFoundException;
import com.neighbourly.userservice.mapper.UserMapper;
import com.neighbourly.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        var userEntity = userMapper.mapDtoToEntity(userDto);
        User createdUserEntity = userRepository.save(userEntity);
        return userMapper.mapEntityToDto(createdUserEntity);
    }


    public UserDto getUser(Long userId) {
        Optional<com.neighbourly.userservice.entity.User> userOpt = userRepository.findById(userId);
        com.neighbourly.userservice.entity.User user = userOpt.orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with id: {0} not found", userId)));
        return userMapper.mapEntityToDto(user);
    }


    public void delete(Long userId){
        userRepository.deleteById(userId);
    }


    public UserDto update(UserDto userDto){
        com.neighbourly.userservice.entity.User userEntity=userMapper.mapDtoToEntity(userDto);
        com.neighbourly.userservice.entity.User updatedUserEntity = userRepository.save(userEntity);
        return userMapper.mapEntityToDto(updatedUserEntity);
    }

}

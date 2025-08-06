package com.neighbourly.user.service;

import com.neighbourly.user.entity.User;
import com.neighbourly.user.exception.UserNotFoundException;
import com.neighbourly.user.mapper.UserMapper;
import com.neighbourly.user.repository.UserRepository;
import com.neighbourly.user.dto.UserDto;
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
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with id: {0} not found", userId)));
        return userMapper.mapEntityToDto(user);
    }


    public void delete(Long userId){
        userRepository.deleteById(userId);
    }


    public UserDto update(UserDto userDto){
        User userEntity=userMapper.mapDtoToEntity(userDto);
        User updatedUserEntity = userRepository.save(userEntity);
        return userMapper.mapEntityToDto(updatedUserEntity);
    }

}

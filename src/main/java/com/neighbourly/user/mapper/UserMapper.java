package com.neighbourly.user.mapper;

import com.neighbourly.user.entity.User;
import com.neighbourly.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    User toEntity(UserDto userDto, @MappingTarget User userEntity);


    default List<UserDto> toDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
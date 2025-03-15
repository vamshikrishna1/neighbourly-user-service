package com.neighbourly.userservice.mapper;

import com.neighbourly.userservice.dto.UserDto;
import com.neighbourly.userservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapDtoToEntity(UserDto userDto);

    UserDto mapEntityToDto(User user);
}

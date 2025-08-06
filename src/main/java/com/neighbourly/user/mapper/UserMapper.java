package com.neighbourly.user.mapper;

import com.neighbourly.user.entity.User;
import com.neighbourly.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapDtoToEntity(UserDto userDto);

    UserDto mapEntityToDto(User user);
}

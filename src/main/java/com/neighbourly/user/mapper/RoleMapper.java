package com.neighbourly.user.mapper;

import com.neighbourly.user.dto.RoleDto;
import com.neighbourly.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

     RoleDto toDto(Role role);
     Role toEntity(RoleDto roleDto);
    Role toEntity(RoleDto roleDto, @MappingTarget Role role);


     default List<RoleDto> toDtoList(List<Role> roles) {
         return roles.stream()
                 .map(this::toDto)
                 .collect(Collectors.toList());
     }

}

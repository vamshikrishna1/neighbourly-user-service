package com.neighbourly.user.service;

import com.neighbourly.user.dto.HeaderInfo;
import com.neighbourly.user.dto.Response;
import com.neighbourly.user.dto.RoleDto;
import com.neighbourly.user.entity.Role;
import com.neighbourly.user.exception.RoleNotFoundException;
import com.neighbourly.user.mapper.RoleMapper;
import com.neighbourly.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class RoleService {


    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public Response<List<RoleDto>> getAllRoles(String name, HeaderInfo headers) {

        Role role = new Role();
        role.setRoleName(name);
        List<Role> roles = roleRepository.findAll(Example.of(role));

        return Response.<List<RoleDto>>builder()
                .data(roleMapper.toDtoList(roles))
                .uuid(headers.getUuid())
                .build();
    }

    public Response<RoleDto> createRole(HeaderInfo headers, RoleDto roleDto) {

        Role role = roleMapper.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        RoleDto createdRoleDto = roleMapper.toDto(savedRole);
        return Response.<RoleDto>builder()
                .data(createdRoleDto)
                .uuid(headers.getUuid())
                .build();

    }

    public Response<RoleDto> getRoleById(Long roleId, HeaderInfo headers) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleId));
        RoleDto roleDto = roleMapper.toDto(role);
        return Response.<RoleDto>builder()
                .data(roleDto)
                .uuid(headers.getUuid())
                .build();
    }

    @Transactional
    public Response<RoleDto> updateRole(Long roleId, RoleDto roleDto, HeaderInfo headers) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleId));

        Role role = roleMapper.toEntity(roleDto, existingRole);
        role.setId(roleId);
        return Response.<RoleDto>builder()
                .data(roleMapper.toDto(role))
                .uuid(headers.getUuid())
                .build();
    }



    public Response<Void> deleteRole(Long roleId, HeaderInfo headers) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleId));
        roleRepository.delete(role);
        return Response.<Void>builder()
                .uuid(headers.getUuid())
                .build();
    }

}

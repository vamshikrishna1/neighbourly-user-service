package com.neighbourly.user.web;

import com.neighbourly.user.dto.HeaderInfo;
import com.neighbourly.user.dto.Response;
import com.neighbourly.user.dto.RoleDto;
import com.neighbourly.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<Response<List<RoleDto>>> getAllRoles(HeaderInfo headers) {
        Response<List<RoleDto>> response = roleService.getAllRoles(headers);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Response<RoleDto>> createRole(HeaderInfo headers, @RequestBody RoleDto roleDto) {
        Response<RoleDto> createdRole = roleService.createRole(headers, roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }


    @GetMapping("/{roleId}")
    public ResponseEntity<Response<RoleDto>> getRoleById(@PathVariable Long roleId, HeaderInfo headers) {
        Response<RoleDto> response = roleService.getRoleById(roleId, headers);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Response<RoleDto>> updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDto, HeaderInfo headers) {
        // Assuming there's an updateRole method in RoleService
        Response<RoleDto> updatedRole = roleService.updateRole(roleId, roleDto, headers);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Response<Void>> deleteRole(@PathVariable Long roleId, HeaderInfo headers) {
        roleService.deleteRole(roleId, headers);
        return ResponseEntity.noContent().build();
    }


}

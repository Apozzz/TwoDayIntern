package com.twoday.warehouse.warehousemodule.role;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.dto.dtomodule.RoleDto;
import com.twoday.warehouse.warehousemodule.response.ApiResponse;
import com.twoday.warehouse.warehousemodule.role.interfaces.RoleService;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {
    
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<ApiResponse> addRole(@RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED.value(), "Role was successfully created.", roleService.save(roleDto)), HttpStatus.CREATED);

    }

}

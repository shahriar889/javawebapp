package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.entity.Role;
import com.julfiker.admin.manager.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    RoleManager roleManager;

    @GetMapping("/roles")
    public List<RoleDTO> getAllRoles(@RequestParam(required = false) String name) {
        return roleManager.findAllRoles().stream()
                .filter(attributesTypeDTO -> name == null || attributesTypeDTO.getName().equals(name))
                .collect(Collectors.toList());
    }

    @GetMapping("/roles/{ID}")
    public RoleDTO getRoleByID(@PathVariable Long ID) {
        return roleManager.findRoleByID(ID);
    }

    @PostMapping("/roles")
    public void createRole(@RequestBody RoleDTO roleDTO) {
        roleManager.saveRole(roleDTO);
    }

    @PutMapping("/roles/{ID}")
    public void updateRole(@PathVariable Long ID, @RequestBody RoleDTO roleDTO) {
        roleManager.updateRole(roleDTO, ID);
    }

    @DeleteMapping("/roles/{ID}")
    @Transactional
    public void deleteRoleByID(@PathVariable long ID) {
        roleManager.deleteRoleByID(ID);
    }
}

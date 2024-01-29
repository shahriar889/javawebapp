package com.julfiker.admin.api.v1;

import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.entity.Role;
import com.julfiker.admin.manager.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    RoleManager roleManager;

    @GetMapping("/detAll")
    public List<RoleDTO> getAllRoles(){
        return roleManager.findAllRoles();
    }

    @GetMapping("/getByID")
    public RoleDTO getRoleByID(@RequestParam Long ID){
        return roleManager.findRoleByID(ID);
    }

    @GetMapping("/getByName")
    public RoleDTO getRoleByName(@RequestParam String name){
        return roleManager.findRoleByName(name);
    }

    @PostMapping("/create")
    public void createRole(@RequestBody RoleDTO roleDTO){
        roleManager.saveRole(roleDTO);
    }

    @PutMapping("/update")
    public void updateRole(RoleDTO roleDTO){
        roleManager.updateRole(roleDTO);
    }

    @DeleteMapping("/delete")
    @Transactional
    public void deleteRoleByID(long ID){
        roleManager.deleteRoleByID(ID);
    }
}

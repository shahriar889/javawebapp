package com.julfiker.admin.manager;

import com.julfiker.admin.dto.RoleDTO;

import java.util.List;

public interface RoleManager {
    void saveRole(RoleDTO roleDTO);
    void updateRole(RoleDTO roleDTO, Long ID);
    void deleteRoleByID(Long ID);
    RoleDTO findRoleByID(Long ID);

    List<RoleDTO> findAllRoles();
}

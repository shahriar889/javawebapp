package com.julfiker.admin.manager;

import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.entity.Role;
import com.julfiker.admin.entity.User;
import com.julfiker.admin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleManagerImpl implements RoleManager{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void saveRole(RoleDTO roleDTO){
        Role role = new Role();
        if(roleDTO.getName() == null){
            System.out.println("Cannot create role without name");
            return;
        }
        role.setName(roleDTO.getName());
        role.setCreation_date(LocalDateTime.now());
        roleRepository.save(role);
    }

    @Override
    public void updateRole(RoleDTO roleDTO, Long ID){
        Role role = roleRepository.findByRoleID(ID);
        if(role == null){
            System.out.println("Could not find role with this ID");
            return;
        }
        if(roleDTO.getName() != null)
            role.setName(roleDTO.getName());

        role.setLast_updated(LocalDateTime.now());
    }

    @Override
    public void deleteRoleByID(Long ID){
        roleRepository.deleteByRoleID(ID);
    }

    @Override
    public RoleDTO findRoleByID(Long ID){
        Role role = roleRepository.findByRoleID(ID);
        if(role == null){
            System.out.println("Could not find Role with this ID");
            return new RoleDTO();
        }
        return convertToDTO(role);
    }


    @Override
    public List<RoleDTO> findAllRoles(){
        List<Role> roleList = roleRepository.findAll();
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for(Role role : roleList)
            roleDTOS.add(convertToDTO(role));
        return roleDTOS;

    }

    private RoleDTO convertToDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        roleDTO.setRoleID(role.getRoleID());
        roleDTO.setCreation_date(role.getCreation_date());
        if(role.getLast_updated() != null)
            roleDTO.setLast_updated(role.getLast_updated());
        List<User> users = role.getUsers();
        List<Long> userIDList = new ArrayList<>();
        for(User user : users)
            userIDList.add(user.getUserID());
        roleDTO.setUserIDList(userIDList);
        return roleDTO;
    }
}

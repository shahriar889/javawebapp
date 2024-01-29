package com.julfiker.admin.manager;

import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.User;


import java.util.List;

public interface UserManager {
    void saveUser(UserDto userDto);

    Long saveUserWithRole(UserDto userDto, RoleDTO roleDTO);

    UserDto findUserByEmail(String email);

    List<UserDto> findAllUsers();

    UserDto findUserByName(String name);

    UserDto findUserByPhone(String phone);

    void updateUser(UserDto userDto);
    void deleteUserByID(Long ID);

    UserDto findUserByID(Long ID);


}
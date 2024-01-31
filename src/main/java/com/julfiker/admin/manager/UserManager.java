package com.julfiker.admin.manager;

import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.dto.UserDto;


import java.util.List;

public interface UserManager {
    void saveUser(UserDto userDto);

    Long saveUserWithRole(UserDto userDto, RoleDTO roleDTO);

    UserDto findUserByEmail(String email);

    List<UserDto> findAllUsers();

    void updateUser(UserDto userDto, Long ID);
    void deleteUserByID(Long ID);

    UserDto findUserByID(Long ID);


}
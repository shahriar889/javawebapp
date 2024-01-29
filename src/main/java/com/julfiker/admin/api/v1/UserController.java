package com.julfiker.admin.api.v1;

import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserManager userManager;

    @GetMapping("/getAll")
    public List<UserDto> getAllUsers(){
        return userManager.findAllUsers();
    }

    @GetMapping("/getByID")
    public UserDto getUserByID(@RequestParam Long ID){
        return userManager.findUserByID(ID);
    }

    @GetMapping("/getByName")
    public UserDto getUserByName(@RequestParam String name){
        return userManager.findUserByName(name);
    }

    @GetMapping("/getByPhone")
    public UserDto getUserByPhone(@RequestParam String phone){
        return userManager.findUserByPhone(phone);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserDto userDto){
        userManager.saveUser(userDto);
    }
    @PutMapping("/update")
    public void updateUser(@RequestBody UserDto userDto){
        userManager.updateUser(userDto);
    }

    @DeleteMapping("/delete")
    @Transactional
    public void deleteUser(@RequestParam Long ID){
        userManager.deleteUserByID(ID);
    }
}

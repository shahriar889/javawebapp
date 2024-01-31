package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserManager userManager;

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String email,
                                     @RequestParam(required = false) String phone) {
        return userManager.findAllUsers().stream()
                .filter(userDto -> (name == null || userDto.getName().equals(name)) &&
                        (email == null || userDto.getEmail().equals(email)) &&
                        (phone == null || userDto.getPhone().equals(phone)))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{ID}")
    public UserDto getUserByID(@PathVariable Long ID) {
        return userManager.findUserByID(ID);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody UserDto userDto) {
        userManager.saveUser(userDto);
    }

    @PutMapping("/users/{ID}")
    public void updateUser(@PathVariable Long ID, @RequestBody UserDto userDto) {
        userManager.updateUser(userDto, ID);
    }

    @DeleteMapping("/users/{ID}")
    @Transactional
    public void deleteUser(@PathVariable Long ID) {
        userManager.deleteUserByID(ID);
    }
}

package com.julfiker.admin.manager;

import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.Role;
import com.julfiker.admin.entity.User;
import com.julfiker.admin.repository.RoleRepository;
import com.julfiker.admin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManagerImpl implements UserManager {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserManagerImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public Long saveUserWithRole(UserDto userDto, RoleDTO roleDTO){
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreation_date(LocalDateTime.now());
        user.setStatus(true);
        user.setPhone(userDto.getPhone());
        Role role = roleRepository.findByName(roleDTO.getName());
        if(role == null) {
            role = new Role();
            role.setName(roleDTO.getName());
            role.setCreation_date(LocalDateTime.now());
        }
        else
            role.setLast_updated(LocalDateTime.now());
        Set<Role> roles = new HashSet<>();
        List<User> users = new ArrayList<>();
        roles.add(role);
        users.add(user);
        user.setRoles(roles);
        role.setUsers(users);
        userRepository.save(user);
        roleRepository.save(role);
        return user.getUserID();
    }

    @Override
    public UserDto findUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        if(user == null){
            System.out.println("Could not find any user with this email");
            return new UserDto();
        }
        return mapToUserDto(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();


        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    public UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
//        String[] str = user.getName().split(" ");
//        userDto.setFirstName(str[0]);
//        userDto.setLastName(str[1]);
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setCreation_Date(user.getCreation_date());
        if(user.getLast_updated() != null)
            userDto.setLast_updated(user.getLast_updated());
        userDto.setUserID(user.getUserID());
        Set<Role> roles = user.getRoles();
        Set<Long> roleIDSet = new HashSet<>();
        for(Role role:roles)
            roleIDSet.add(role.getRoleID());
        userDto.setRoleIDSet(roleIDSet);
        return userDto;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public void updateUser(UserDto userDto, Long ID){
        User user = userRepository.findByUserID(ID);
        if(user == null){
            System.out.println("Could not find any User with this ID");
            return;
        }
        if(userDto.getName() != null)
            user.setName(userDto.getName());
        if(userDto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(userDto.getPhone() != null)
            user.setPhone(userDto.getPhone());
        user.setLast_updated(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void deleteUserByID(Long ID){
        userRepository.deleteByUserID(ID);
    }

    @Override
    public UserDto findUserByID(Long ID){
        User user = userRepository.findByUserID(ID);
        if(user == null){
            System.out.println("Could not find any user with this ID");
            return new UserDto();
        }
        return mapToUserDto(user);
    }
}
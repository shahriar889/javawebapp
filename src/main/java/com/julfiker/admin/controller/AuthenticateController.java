package com.julfiker.admin.controller;

import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.User;
import com.julfiker.admin.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class AuthenticateController {

    @Autowired
    private UserManager userManager;

    @RequestMapping("/login")
    public String login() {
        return "auth/login";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "auth/register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        System.out.println(userDto.getEmail());
        UserDto existingUser = userManager.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            userDto.setCreation_Date(LocalDateTime.now());
            userDto.setStatus(true);
            model.addAttribute("user", userDto);
            return "auth/register";
        }


        userManager.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userManager.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}

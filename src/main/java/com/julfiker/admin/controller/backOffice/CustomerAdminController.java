package com.julfiker.admin.controller.backOffice;


import com.julfiker.admin.dto.*;
import com.julfiker.admin.entity.Customer;
import com.julfiker.admin.manager.CustomerManager;
import com.julfiker.admin.manager.MediaManager;
import com.julfiker.admin.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerAdminController {
    @Autowired
    CustomerManager customerManager;
    @Autowired
    ControllerHelpers helpers;
    @Autowired
    MediaManager mediaManager;
    @Autowired
    UserManager userManager;

    @GetMapping("/customers/new")
    public String showCustomerEntryForm(Model model) {
        CustomerUserDTOWrapper wrapper = new CustomerUserDTOWrapper();
        model.addAttribute("wrapper", wrapper);
        return "admin/customer-entry-form";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(@Valid @ModelAttribute("customerUser") CustomerUserDTOWrapper wrapper,
                               BindingResult result,
                               Model model,
                               @RequestParam(value = "file", required = false) MultipartFile[] files) {
        String name = wrapper.getCustomerDTO().getName();
        wrapper.getUserDto().setName(name);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            CustomerUserDTOWrapper wrapper2 = new CustomerUserDTOWrapper();
            model.addAttribute("wrapper", wrapper2);
            return "admin/customer-entry-form";
        }
        if (files != null && files.length > 0) {
            Long savedMediaID = helpers.saveImages(files);
            wrapper.getCustomerDTO().setMediaID(savedMediaID);
        } else
            wrapper.getCustomerDTO().setMediaID(null);
        customerManager.saveCustomer(wrapper.getCustomerDTO(), wrapper.getUserDto());
        return "redirect:/customers";
    }

    @GetMapping("/customers/{ID}/edit")
    public String editCustomer(@PathVariable Long ID, Model model){
        CustomerDTO customerDTO = customerManager.findCustomerByID(ID);
        UserDto userDto = userManager.findUserByID(customerDTO.getUserID());
        CustomerUserDTOWrapper wrapper = new CustomerUserDTOWrapper(customerDTO, userDto);
        model.addAttribute("wrapper",wrapper);
        return "admin/customer-entry-form";
    }

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        List<CustomerDTO> customerDTOS = customerManager.findAllCustomers();
        List<UserDto> userDTOS = userManager.findAllUsers();
        List<Long> customerUserIds = customerDTOS.stream()
                .map(CustomerDTO::getUserID)
                .collect(Collectors.toList());
        userDTOS.removeIf(userDto -> !customerUserIds.contains(userDto.getUserID()));
        List<MediaDTO> mediaDTOS = mediaManager.findAllMedia();
        mediaDTOS.removeIf(mediaDTO -> mediaDTO.getCustomerID() == null);
        model.addAttribute("customers", customerDTOS);
        model.addAttribute("users", userDTOS);
        model.addAttribute("medias", mediaDTOS);
        return "admin/customer-list";
    }
}

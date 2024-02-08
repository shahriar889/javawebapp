package com.julfiker.admin.controller.backOffice;

import com.julfiker.admin.dto.CustomerDTO;
import com.julfiker.admin.dto.CustomerUserDTOWrapper;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.manager.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerAdminController {
    @Autowired
    CustomerManager customerManager;

    @GetMapping("/customers/new")
    public String showCustomerEntryForm(Model model){
        System.out.println("In controller");
        CustomerUserDTOWrapper wrapper = new CustomerUserDTOWrapper();
        wrapper.setCustomerDTO(new CustomerDTO());
        wrapper.setUserDto(new UserDto());
        model.addAttribute("wrapper", wrapper);
        return "admin/customer-entry-form";
    }

//    @PostMapping("/customers/save")
//    public String saveCustomer(@Valid @ModelAttribute("customerUser")CustomerUserDTOWrapper wrapper,
//                               BindingResult result,
//                               Model model,
//                               @RequestParam(value = "file", required = false) MultipartFile[] files){
//        return "New";
//    }
}

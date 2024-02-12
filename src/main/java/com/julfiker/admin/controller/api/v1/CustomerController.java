package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.CustomerDTO;
import com.julfiker.admin.dto.CustomerUserDTOWrapper;
import com.julfiker.admin.dto.PaymentInfoDTO;
import com.julfiker.admin.manager.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    @Autowired
    CustomerManager customerManager;

    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers(@RequestParam(required = false) String name, @RequestParam(required = false) String address) {
        return customerManager.findAllCustomers().stream()
                .filter(customerDTO ->
                        (name == null || customerDTO.getName().equals(name)) &&
                                (address == null || customerDTO.getAddress().equals(address)))
                .collect(Collectors.toList());

    }

    @GetMapping("/customers/{ID}")
    public CustomerDTO getCustomerByID(@PathVariable Long ID) {
        return customerManager.findCustomerByID(ID);
    }


    @PostMapping("/customers")
    public void createCustomer(@RequestBody CustomerUserDTOWrapper customerUserDTOWrapper) {
        customerManager.saveCustomer(customerUserDTOWrapper.getCustomerDTO(), customerUserDTOWrapper.getUserDto());
    }

    @PutMapping("/customers/{ID}")
    public void updateCustomer(@PathVariable Long ID, @RequestBody CustomerDTO customerDTO) {
        customerManager.updateCustomer(customerDTO, ID);
    }

    @DeleteMapping("/customers/{ID}")
    @Transactional
    public void deleteCustomerByID(@PathVariable Long ID) {
        customerManager.deleteCustomerByID(ID);
    }

    @PutMapping("/customers/{customerID}/medias/{mediaID}")
    public void addMediaToCustomer(@PathVariable Long customerID, @PathVariable Long mediaID) {
        customerManager.addMediaToCustomer(customerID, mediaID);
    }

    @PostMapping("/customers/{ID}/payment-infos")
    public void addPaymentInfoToCustomer(@PathVariable Long ID, @RequestBody PaymentInfoDTO paymentInfoDTO){
        customerManager.savePIForCustomer(ID, paymentInfoDTO);
    }

    @GetMapping("/customers/{ID}/payment-infos")
    public List<PaymentInfoDTO> getAllPaymentInfos(@PathVariable Long ID, @RequestParam(required = false) String cardType){
        return customerManager.getAllPIByCustomer(ID).stream()
                .filter(paymentInfoDTO -> (cardType == null || paymentInfoDTO.getCardType().equals(cardType)))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/customers/{ID}/payment-infos/{ID2}")
    @Transactional
    public void deleteCustomersPIByID(@PathVariable Long ID, @PathVariable Long ID2){
        customerManager.deletePIFromCustomer(ID, ID2);
    }




}

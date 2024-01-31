package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CustomerDTO;
import com.julfiker.admin.dto.PaymentInfoDTO;
import com.julfiker.admin.dto.UserDto;

import java.util.List;

public interface CustomerManager {
    void saveCustomer(CustomerDTO customerDTO, UserDto userDto);
    void updateCustomer(CustomerDTO customerDTO, Long ID);
    void deleteCustomerByID(Long ID);

    List<CustomerDTO> findAllCustomers();
    CustomerDTO findCustomerByID(Long ID);

    void savePIForCustomer(Long ID, PaymentInfoDTO paymentInfoDTO);

    void addMediaToCustomer(Long customerID, Long mediaID);
}
package com.julfiker.admin.manager;

import com.julfiker.admin.dto.*;
import com.julfiker.admin.entity.*;
import com.julfiker.admin.repository.CustomerRepository;
import com.julfiker.admin.repository.MediaRepository;
import com.julfiker.admin.repository.PaymentInfoRepository;
import com.julfiker.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerManagerImpl implements CustomerManager{

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserManager userManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    PaymentInfoRepository paymentInfoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private CustomerDTO convertToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setDescription(customer.getDescription());
        customerDTO.setCustomerID(customer.getCustomerID());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setUserID(customer.getUser().getUserID());
        customerDTO.setCreation_date(customer.getCreation_date());
        if(customer.getLast_updated() != null)
            customerDTO.setLast_updated(customer.getLast_updated());
        if(customer.getCart() != null)
            customerDTO.setCartID(customer.getCart().getCartID());
        if (customer.getOrder() != null)
            customerDTO.setOrderID(customer.getOrder().getOrderID());
        if(customer.getMedia() != null)
            customerDTO.setMediaID(customer.getMedia().getMediaID());
        List<PaymentInfo> paymentInfos = customer.getPaymentInfos();
        List<Long> pI_IDs = new ArrayList<>();
        for(PaymentInfo paymentInfo : paymentInfos)
            pI_IDs.add(paymentInfo.getPaymentInfoID());
        customerDTO.setPaymentInfoIDs(pI_IDs);
        return customerDTO;
    }

    private PaymentInfoDTO convertToPI_DTO(PaymentInfo paymentInfo){
        PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO();
        paymentInfoDTO.setPaymentInfoID(paymentInfo.getPaymentInfoID());
        paymentInfoDTO.setCardType(paymentInfo.getCardType());
        paymentInfoDTO.setCardNumber(paymentInfo.getCardNumber());
        paymentInfoDTO.setCVV(paymentInfo.getCVV());
        paymentInfoDTO.setExpirationDate(paymentInfo.getExpirationDate());
        paymentInfoDTO.setCustomerID(paymentInfo.getCustomer().getCustomerID());
        paymentInfoDTO.setCreation_date(paymentInfo.getCreation_date());
        if(paymentInfo.getLast_updated() != null)
            paymentInfoDTO.setLast_updated(paymentInfo.getLast_updated());
        return paymentInfoDTO;
    }

    @Override
    public void saveCustomer(CustomerDTO customerDTO, UserDto userDto){
        if(customerDTO.getName() == null || customerDTO.getDescription() == null ||
        customerDTO.getAddress() == null){
            System.out.println("Do not have enough info to create customer.");
            return;
        }
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setDescription(customerDTO.getDescription());
        customer.setCreation_date(LocalDateTime.now());
        List<PaymentInfo> paymentInfos = new ArrayList<>();
        customer.setPaymentInfos(paymentInfos);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("Customer");
        Long userID = userManager.saveUserWithRole(userDto, roleDTO);
        User user = userRepository.findByUserID(userID);
        customer.setUser(user);
        customerRepository.save(customer);
    }
    @Override
    public void updateCustomer(CustomerDTO customerDTO, Long ID){
        Customer customer = customerRepository.findByCustomerID(ID);
        if(customer == null){
            System.out.println("Could not find customer associated with the given ID");
            return;
        }
        if(customerDTO.getName() != null)
            customer.setName(customerDTO.getName());
        if(customerDTO.getDescription() != null)
            customer.setDescription(customerDTO.getDescription());
        if(customerDTO.getAddress() != null)
            customer.setAddress(customerDTO.getAddress());
        customer.setLast_updated(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerByID(Long ID){
        customerRepository.deleteCustomerByCustomerID(ID);
    }

    @Override
    public List<CustomerDTO> findAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer : customers)
            customerDTOS.add(convertToDTO(customer));
        return customerDTOS;
    }

    @Override
    public CustomerDTO findCustomerByID(Long ID){
        Customer customer = customerRepository.findByCustomerID(ID);
        if(customer == null){
            System.out.println("Could not find any customer with this ID");
            return new CustomerDTO();
        }
        return convertToDTO(customer);
    }


    @Override
    @Transactional
    public void addMediaToCustomer(Long customerID, Long mediaID){
        Customer customer = customerRepository.findByCustomerID(customerID);
        if(customer == null){
            System.out.println("Could not find customer with this ID");
            return;
        }

        Media oldMedia = new Media();
        if(customer.getMedia() != null){
            oldMedia = customer.getMedia();
        }
        Media media = mediaRepository.findByMediaID(mediaID);
        if(media == null){
            System.out.println("Could not find media with this ID");
            return;
        }
        customer.setMedia(media);
        media.setCustomer(customer);
        mediaRepository.save(media);
        customerRepository.save(customer);
        if(oldMedia.getMediaID() != null)
            mediaRepository.deleteByMediaID(oldMedia.getMediaID());
    }

    @Override
    public void savePIForCustomer(Long ID, PaymentInfoDTO paymentInfoDTO){
        Customer customer = customerRepository.findByCustomerID(ID);
        if(customer == null){
            System.out.println("Could not find any customer with this ID");
            return;
        }
        if(paymentInfoDTO.getCardType() == null || paymentInfoDTO.getCardNumber() == null ||
        paymentInfoDTO.getCVV() == null || paymentInfoDTO.getExpirationDate() == null){
            System.out.println("Do not have enough info to make Payment Info");
            return;
        }
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardNumber(passwordEncoder.encode(paymentInfoDTO.getCardNumber()));
        paymentInfo.setCVV(passwordEncoder.encode(paymentInfoDTO.getCVV()));
        paymentInfo.setCardType(paymentInfoDTO.getCardType());
        paymentInfo.setExpirationDate(paymentInfoDTO.getExpirationDate());
        paymentInfo.setCreation_date(LocalDateTime.now());
        paymentInfo.setCustomer(customer);
        customer.getPaymentInfos().add(paymentInfo);
        customer.setLast_updated(LocalDateTime.now());
        paymentInfoRepository.save(paymentInfo);
        customerRepository.save(customer);

    }
}

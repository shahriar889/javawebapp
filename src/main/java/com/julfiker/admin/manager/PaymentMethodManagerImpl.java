package com.julfiker.admin.manager;

import com.julfiker.admin.dto.PaymentMethodDTO;
import com.julfiker.admin.entity.PaymentMethod;
import com.julfiker.admin.entity.Seller;
import com.julfiker.admin.repository.PaymentMethodRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentMethodManagerImpl implements PaymentMethodManager {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodDTO convertToDTO(PaymentMethod pm) {
        PaymentMethodDTO pmDTO = new PaymentMethodDTO();
        pmDTO.setPaymentMethodID(pm.getPaymentMethodID());
        pmDTO.setName(pm.getName());
        pmDTO.setCreation_date(pm.getCreation_date());
        List<Seller> sellerList = pm.getSellers();
        List<Long> sellerIDList = new ArrayList<>();
        for (Seller seller : sellerList)
            sellerIDList.add(seller.getSellerID());
        pmDTO.setSellerIDList(sellerIDList);
        if (pm.getLast_updated() != null)
            pmDTO.setLast_updated(pm.getLast_updated());
        return pmDTO;
    }


    @Override
    public void savePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        if (paymentMethodDTO.getName() == null) {
            System.out.println("Cannot create payment method without name");
            return;
        }
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(paymentMethodDTO.getName());
        paymentMethod.setSellers(new ArrayList<>());
        if (paymentMethod.getCreation_date() == null)
            paymentMethod.setCreation_date(LocalDateTime.now());
        paymentMethodRepository.save(paymentMethod);

    }

    @Override
    public List<PaymentMethodDTO> findAll() {

        List<PaymentMethod> pmList = paymentMethodRepository.findAll();
        List<PaymentMethodDTO> pmDTOList = new ArrayList<>();
        for (PaymentMethod pm : pmList)
            pmDTOList.add(convertToDTO(pm));
        return pmDTOList;
    }

    @Override
    public PaymentMethodDTO findByID(Long ID) {

        PaymentMethod pm = paymentMethodRepository.getPaymentMethodByPaymentMethodID(ID);
        if (pm == null) {
            System.out.println("Could not find Payment Method with this ID");
            return new PaymentMethodDTO();
        }
        return convertToDTO(pm);
    }

    @Override
    public void updatePaymentMethod(PaymentMethodDTO paymentMethodDTO, Long ID) {
        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentMethodByPaymentMethodID(ID);
        if (paymentMethod == null) {
            System.out.println("Could not find payment method associated with the ID");
            return;
        }
        if (paymentMethodDTO.getName() != null)
            paymentMethod.setName(paymentMethodDTO.getName());
        paymentMethod.setLast_updated(LocalDateTime.now());
        paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public void deletePaymentMethod(Long ID) {
        paymentMethodRepository.deleteByPaymentMethodID(ID);
    }
}

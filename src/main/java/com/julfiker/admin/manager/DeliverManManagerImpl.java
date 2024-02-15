package com.julfiker.admin.manager;

import com.julfiker.admin.dto.DeliveryManDTO;
import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.DeliveryMan;
import com.julfiker.admin.entity.Order;
import com.julfiker.admin.entity.Seller;
import com.julfiker.admin.entity.ShippingMethod;
import com.julfiker.admin.repository.DeliveryManRepository;
import com.julfiker.admin.repository.OrderRepository;
import com.julfiker.admin.repository.ShippingMethodRepository;
import com.julfiker.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliverManManagerImpl implements DeliveryManManager{
    @Autowired
    DeliveryManRepository manRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShippingMethodRepository methodRepository;
    @Autowired
    UserManager userManager;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public DeliveryManDTO convertToDTO(DeliveryMan deliveryMan) {
        DeliveryManDTO manDTO = new DeliveryManDTO();
        manDTO.setDeliveryManID(deliveryMan.getDeliveryManID());
        manDTO.setName(deliveryMan.getName());
        manDTO.setAvailable(deliveryMan.isAvailable());
        manDTO.setCreation_date(deliveryMan.getCreation_date());
        manDTO.setRating(deliveryMan.getRating());
        manDTO.setNumRatings(deliveryMan.getNumRatings());
        manDTO.setContactInfo(deliveryMan.getContactInfo());
        manDTO.setLicensePlate(deliveryMan.getLicensePlate());
        manDTO.setVehicleType(deliveryMan.getVehicleType());
        manDTO.setUserID(deliveryMan.getUser().getUserID());
        if(deliveryMan.getLast_updated() != null)
            manDTO.setLast_updated(deliveryMan.getLast_updated());
        if(deliveryMan.getOrder() != null)
            manDTO.setOrderID(deliveryMan.getOrder().getOrderID());
        return manDTO;
    }

    @Override
    public void saveDeliveryMan(DeliveryManDTO deliveryManDTO, UserDto userDto) {
        if(deliveryManDTO.getContactInfo() == null || deliveryManDTO.getName() == null
        || deliveryManDTO.getLicensePlate() == null || deliveryManDTO.getVehicleType() == null){
            System.out.println("Not enough info to save delivery man");
            return;
        }
        DeliveryMan deliveryMan = manRepository.findByDeliveryManID(deliveryManDTO.getDeliveryManID());
        Long userID = 0L;
        if(deliveryMan == null){
            deliveryMan = new DeliveryMan();
            deliveryMan.setCreation_date(LocalDateTime.now());
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName("Delivery Man");
            userID = userManager.saveUserWithRole(userDto, roleDTO);
        }
        else
            deliveryMan.setLast_updated(LocalDateTime.now());
        deliveryMan.setContactInfo(deliveryManDTO.getContactInfo());
        deliveryMan.setName(deliveryManDTO.getName());
        deliveryMan.setRating(new BigDecimal(0));
        deliveryMan.setUser(userRepository.findByUserID(userID));
        deliveryMan.setAvailable(deliveryManDTO.isAvailable());
        deliveryMan.setLicensePlate(deliveryManDTO.getLicensePlate());
        deliveryMan.setVehicleType(deliveryManDTO.getVehicleType());
        deliveryMan.setNumRatings(0L);
        manRepository.save(deliveryMan);
    }

    @Override
    public DeliveryManDTO findByID(Long ID) {
        DeliveryMan man = manRepository.findByDeliveryManID(ID);
        if(man == null){
            System.out.println("Could not find Delivery man with the ID");
            return new DeliveryManDTO();
        }
        return convertToDTO(man);
    }

    @Override
    public List<DeliveryManDTO> findAll() {
        List<DeliveryManDTO> manDTOs = new ArrayList<>();
        for(DeliveryMan man : manRepository.findAll())
            manDTOs.add(convertToDTO(man));
        return manDTOs;
    }

    @Override
    public DeliveryManDTO findByLicensePlate(String msg) {
        DeliveryMan man = manRepository.findByLicensePlate(msg);
        if(man == null){
            System.out.println("Could not find Delivery man with the License Plate");
            return new DeliveryManDTO();
        }
        return convertToDTO(man);
    }

    @Override
    public List<DeliveryManDTO> findByAvailable(boolean check) {
        List<DeliveryManDTO> manDTOs = new ArrayList<>();
        for(DeliveryMan man : manRepository.findByAvailable(check))
            manDTOs.add(convertToDTO(man));
        return manDTOs;
    }

    @Override
    public void changeAvailable(Long ID) {
        DeliveryMan man = manRepository.findByDeliveryManID(ID);
        if(man == null){
            System.out.println("Could not find Delivery man with the ID");
            return;
        }
        man.setAvailable(!man.isAvailable());
        manRepository.save(man);
    }

    @Override
    @Transactional
    public void deleteDeliveryManByID(Long ID) {
        DeliveryMan man = manRepository.findByDeliveryManID(ID);
        if(man == null){
            System.out.println("Could not find Delivery man with the ID");
            return;
        }
        if(man.getOrder() != null){
            Order order = man.getOrder();
            order.setDeliveryMan(null);
        }
        man.setOrder(null);
        manRepository.deleteById(ID);
    }

    @Override
    public void addRating(Long ID, BigDecimal rating) {
        DeliveryMan man = manRepository.findByDeliveryManID(ID);
        if(man == null){
            System.out.println("Could not find Delivery man with the ID");
            return;
        }
        Long curNumRatings = man.getNumRatings();
        BigDecimal curTotalRating = man.getRating().multiply(BigDecimal.valueOf(curNumRatings));
        BigDecimal newTotalRating = curTotalRating.add(rating);
        BigDecimal newNumRatings = BigDecimal.valueOf(curNumRatings + 1);
        BigDecimal newRating = newTotalRating.divide(newNumRatings, RoundingMode.HALF_UP);
        man.setRating(newRating);
        man.setNumRatings(curNumRatings+1L);
        man.setLast_updated(LocalDateTime.now());
        manRepository.save(man);
    }

    @Override
    public void confirmDelivery(Long manID) {
        DeliveryMan man = manRepository.findByDeliveryManID(manID);
        if(man == null){
            System.out.println("Could not find Delivery man with the ID");
            return;
        }
        if(man.getOrder() == null){
            System.out.println("This delivery Man is not assigned to an order");
            return;
        }
        Order order = man.getOrder();
        order.setStatus(true);
        man.setOrder(null);
        order.setDeliveryMan(null);
        orderRepository.save(order);
        manRepository.save(man);

    }
}

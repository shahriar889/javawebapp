package com.julfiker.admin.manager;

import com.julfiker.admin.dto.PaymentMethodDTO;
import com.julfiker.admin.dto.RoleDTO;
import com.julfiker.admin.dto.SellerDTO;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.*;
import com.julfiker.admin.repository.MediaRepository;
import com.julfiker.admin.repository.PaymentMethodRepository;
import com.julfiker.admin.repository.SellerRepository;
import com.julfiker.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SellerManagerImpl implements SellerManager{

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    UserManager userManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    private final Long defaultNumRating = 0L;
    private final BigDecimal defaultRating = BigDecimal.valueOf(0.0);

    private SellerDTO convertToDTO(Seller seller){
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setSellerID(seller.getSellerID());
        sellerDTO.setName(seller.getName());
        sellerDTO.setDescription(seller.getDescription());
        sellerDTO.setRating(seller.getRating());
        sellerDTO.setNumRatings(sellerDTO.getNumRatings());
        sellerDTO.setSocialMedia(seller.getSocialMedia());
        sellerDTO.setAddress(seller.getAddress());
        List<Item> items = seller.getItems();
        List<Long> itemIDList = new ArrayList<>();
        for(Item item: items)
            itemIDList.add(item.getItemID());
        sellerDTO.setItemIDList(itemIDList);
        if(seller.getUser() != null)
            sellerDTO.setUserID(seller.getUser().getUserID());
        if (seller.getMedia() != null)
            sellerDTO.setMediaID(seller.getMedia().getMediaID());
        Set<PaymentMethod> pmList = seller.getPaymentMethods();
        List<Long> pmIDLIST = new ArrayList<>();
        for(PaymentMethod pm: pmList)
            pmIDLIST.add(pm.getPaymentMethodID());
        sellerDTO.setPaymentMethodIDList(pmIDLIST);
        return sellerDTO;
    }

    @Override
    public void saveSeller(SellerDTO sellerDTO, UserDto userDto){
        if(sellerDTO.getName() == null || sellerDTO.getDescription() == null ||
        sellerDTO.getAddress() == null || sellerDTO.getSocialMedia() == null ||
        sellerDTO.getReturnPolicy() == null){
            System.out.println("Need more info to create Seller");
            return;
        }
        Seller seller = new Seller();
        seller.setRating(defaultRating);
        seller.setNumRatings(defaultNumRating);
        seller.setDescription(sellerDTO.getDescription());
        seller.setAddress(sellerDTO.getAddress());
        seller.setName(sellerDTO.getName());
        seller.setReturnPolicy(sellerDTO.getReturnPolicy());
        seller.setSocialMedia(sellerDTO.getSocialMedia());
        List<Item> itemList = new ArrayList<>();
        seller.setItems(itemList);
        if(seller.getCreation_date() == null)
            seller.setCreation_date(LocalDateTime.now());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("Seller");
        Long userID = userManager.saveUserWithRole(userDto,roleDTO);
        User user = userRepository.findByUserID(userID);
        seller.setUser(user);
        sellerRepository.save(seller);
    }

    @Override
    public void updateSeller(SellerDTO sellerDTO){
        if(sellerDTO.getSellerID() == null){
            System.out.println("Cannot update seller without ID");
            return;
        }
        Seller seller = sellerRepository.findBySellerID(sellerDTO.getSellerID());
        if(seller == null){
            System.out.println("Could not find seller associated with the given ID");
            return;
        }
        if(sellerDTO.getName() != null)
            seller.setName(sellerDTO.getName());
        if(sellerDTO.getDescription() != null)
            seller.setDescription(sellerDTO.getDescription());
        if(sellerDTO.getSocialMedia() != null)
            seller.setSocialMedia(sellerDTO.getSocialMedia());
        if(sellerDTO.getAddress() != null)
            seller.setAddress(sellerDTO.getAddress());
        if(sellerDTO.getReturnPolicy() != null)
            seller.setReturnPolicy(sellerDTO.getReturnPolicy());
        seller.setLast_updated(LocalDateTime.now());
        sellerRepository.save(seller);
    }

    @Override
    public void deleteSellerByID(Long ID){
        System.out.println("Hit seller service");
        Seller seller = sellerRepository.findBySellerID(ID);
        Long userID = seller.getUser().getUserID();
        userRepository.deleteByUserID(userID);
        int r = sellerRepository.deleteBySellerID(ID);
        System.out.println(r);
    }

    @Override
    public SellerDTO findBySellerID(Long ID){

        Seller seller = sellerRepository.findBySellerID(ID);
        if(seller == null){
            System.out.println("Could not find find seller with this ID");
            return new SellerDTO();
        }
        return convertToDTO(seller);
    }

    @Override
    public SellerDTO findByName(String name){

        Seller seller = sellerRepository.findByName(name);
        if(seller == null){
            System.out.println("Could not find find seller with this name");
            return new SellerDTO();
        }
        return convertToDTO(seller);
    }

    @Override
    public SellerDTO findByAddress(String address){

        Seller seller = sellerRepository.findByAddress(address);
        if(seller == null){
            System.out.println("Could not find find seller with this address");
            return new SellerDTO();
        }
        return convertToDTO(seller);
    }

    @Override
    public SellerDTO findBySocialMedia(String socialMedia){

        Seller seller = sellerRepository.findBySocialMedia(socialMedia);
        if(seller == null){
            System.out.println("Could not find find seller with this social Media");
            return new SellerDTO();
        }
        return convertToDTO(seller);
    }

    @Override
    public List<SellerDTO> findAllByRating(BigDecimal rating){

        List<Seller> sellerList = sellerRepository.findAllByRating(rating);
        List<SellerDTO> sellerDTOS = new ArrayList<>();
        for(Seller seller : sellerList)
            sellerDTOS.add(convertToDTO(seller));
        return sellerDTOS;
    }

    @Override
    public List<SellerDTO> findAllByReturnPolicy(String returnPolicy){
        List<Seller> sellerList = sellerRepository.findAllByReturnPolicy(returnPolicy);
        List<SellerDTO> sellerDTOS = new ArrayList<>();
        for(Seller seller : sellerList)
            sellerDTOS.add(convertToDTO(seller));
        return sellerDTOS;
    }

    @Override
    public List<SellerDTO> findAll(){

        List<Seller> sellerList = sellerRepository.findAll();
        List<SellerDTO> sellerDTOS = new ArrayList<>();
        for(Seller seller : sellerList)
            sellerDTOS.add(convertToDTO(seller));
        return sellerDTOS;
    }

    @Override
    public void setSellerRating(Long ID, BigDecimal rating){
        Seller seller = sellerRepository.findBySellerID(ID);
        if(seller == null){
            System.out.println("No Seller found associated with this ID");
            return;
        }
        Long curNumRatings = seller.getNumRatings();
        BigDecimal curTotalRating = seller.getRating().multiply(BigDecimal.valueOf(curNumRatings));
        BigDecimal newTotalRating = curTotalRating.add(rating);
        BigDecimal newNumRatings = BigDecimal.valueOf(curNumRatings + 1);
        BigDecimal newRating = newTotalRating.divide(newNumRatings, RoundingMode.HALF_UP);
        seller.setRating(newRating);
        seller.setNumRatings(curNumRatings+1L);
        seller.setLast_updated(LocalDateTime.now());
        sellerRepository.save(seller);
    }

    @Override
    public void addMediaToSeller(Long sellerID, Long mediaID){
        Seller seller = sellerRepository.findBySellerID(sellerID);
        if(seller == null){
            System.out.println("Could not find seller with this ID");
            return;
        }
        Media media = mediaRepository.findByMediaID(mediaID);
        if(media == null){
            System.out.println("Could not find media with this ID");
            return;
        }
        seller.setMedia(media);
        media.setSeller(seller);
        mediaRepository.save(media);
        sellerRepository.save(seller);
    }

    @Override
    public  void addAssociationWithPaymentMethod(Long sellerID, Long pmID){
        Seller seller = sellerRepository.findBySellerID(sellerID);
        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentMethodByPaymentMethodID(pmID);
        if(seller == null || paymentMethod == null){
            System.out.println("Could not find the entity(s) with the ID(s)");
            return;
        }
        seller.getPaymentMethods().add(paymentMethod);
        seller.setLast_updated(LocalDateTime.now());
        paymentMethod.getSellers().add(seller);
        paymentMethod.setLast_updated(LocalDateTime.now());
        sellerRepository.save(seller);
        paymentMethodRepository.save(paymentMethod);
    }
}

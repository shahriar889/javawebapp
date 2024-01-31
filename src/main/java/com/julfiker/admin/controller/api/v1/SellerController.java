package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.SellerDTO;
import com.julfiker.admin.dto.SellerUserDtoWrapper;
import com.julfiker.admin.manager.SellerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class SellerController {

    @Autowired
    SellerManager sellerManager;

    @GetMapping("/sellers")
    public List<SellerDTO> getAll(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String address,
                                  @RequestParam(required = false) String socialMedia,
                                  @RequestParam(required = false) BigDecimal ratings,
                                  @RequestParam(required = false) String returnPolicy){
        return sellerManager.findAll().stream()
                .filter(seller -> (name == null || seller.getName().equals(name))
                        && (address == null || seller.getAddress().equals(address))
                        && (socialMedia == null || seller.getSocialMedia().equals(socialMedia))
                        && (ratings == null || seller.getRating().compareTo(ratings) >= 0)
                        && (returnPolicy == null || seller.getReturnPolicy().equals(returnPolicy)))
                .collect(Collectors.toList());


    }
    @GetMapping("/sellers/{ID}")
    public SellerDTO getSellerByID(@PathVariable Long ID){
        return sellerManager.findBySellerID(ID);
    }

    @PostMapping("/sellers")
    public void createSeller(@RequestBody SellerUserDtoWrapper wrapper){
        sellerManager.saveSeller(wrapper.getSellerDTO(),wrapper.getUserDto());
    }
    @PutMapping("/sellers/{ID}")
    public void updateSeller(@PathVariable Long ID, @RequestBody SellerDTO sellerDTO){
        sellerManager.updateSeller(sellerDTO, ID);
    }

    @DeleteMapping("/sellers/{ID}")
    @Transactional
    public void deleteSeller(@PathVariable Long ID){
        System.out.println("Hit seller controller");
        sellerManager.deleteSellerByID(ID);
    }
    @PutMapping("/sellers/{ID}/ratings")
    public void addRatingToSeller(@PathVariable Long ID, @RequestBody BigDecimal rating){
        sellerManager.setSellerRating(ID, rating);
    }

    @PutMapping("/sellers/{sellerID}/medias/{mediaID}")
    public void addMediaToSeller(@PathVariable Long sellerID, @PathVariable Long mediaID){
        sellerManager.addMediaToSeller(sellerID, mediaID);
    }

    @PutMapping("/sellers/{sellerID}/payment-methods/{pmID}")
    public void addAssociationWithPM(@PathVariable Long sellerID, @PathVariable Long pmID){
        sellerManager.addAssociationWithPaymentMethod(sellerID, pmID);
    }

}

package com.julfiker.admin.api.v1;

import com.julfiker.admin.dto.SellerDTO;
import com.julfiker.admin.entity.Seller;
import com.julfiker.admin.manager.SellerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

    @Autowired
    SellerManager sellerManager;

    @GetMapping("/getAll")
    public List<SellerDTO> getAll(){
       return sellerManager.findAll();
    }
    @GetMapping("/getByID")
    public SellerDTO getSellerByID(@RequestParam Long ID){
        return sellerManager.findBySellerID(ID);
    }
    @GetMapping("/getByName")
    public SellerDTO getSellerByName(@RequestParam String name){
        return sellerManager.findByName(name);
    }
    @GetMapping("/getByAddress")
    public SellerDTO getSellerByAddress(@RequestParam String address){
        return sellerManager.findByAddress(address);
    }
    @GetMapping("/getBySocialMedia")
    public SellerDTO getSellerBySocialMedia(@RequestParam String socialMedia){
        return sellerManager.findBySocialMedia(socialMedia);
    }
    @GetMapping("/getAllByRating")
    public List<SellerDTO> getAllSellersByRating(@RequestParam BigDecimal rating){
        return sellerManager.findAllByRating(rating);
    }
    @GetMapping("/getAllByReturnPolicy")
    public List<SellerDTO> getAllSellersByReturnPolicy(@RequestParam String returnPolicy){
        return sellerManager.findAllByReturnPolicy(returnPolicy);
    }

    @PostMapping("/create")
    void createSeller(@RequestBody SellerDTO sellerDTO){
        sellerManager.saveSeller(sellerDTO);
    }
    @PutMapping("/update")
    void updateSeller(@RequestBody SellerDTO sellerDTO){
        sellerManager.updateSeller(sellerDTO);
    }

    @DeleteMapping("/delete")
    @Transactional
    public void deleteSeller(@RequestParam Long ID){
        sellerManager.deleteSellerByID(ID);
    }
    @PutMapping("/addRating")
    public void addRatingToSeller(@RequestParam Long ID, @RequestBody BigDecimal rating){
        sellerManager.setSellerRating(ID, rating);
    }

    @PutMapping("/addAssociationWithPM")
    public void addAssociationWithPM(@RequestParam Long sellerID, @RequestParam Long pmID){
        sellerManager.addAssociationWithPaymentMethod(sellerID, pmID);
    }

}
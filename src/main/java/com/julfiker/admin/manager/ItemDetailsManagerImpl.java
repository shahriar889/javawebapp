package com.julfiker.admin.manager;

import com.julfiker.admin.dto.ItemDTO;
import com.julfiker.admin.dto.ItemDetailsDTO;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.entity.ItemDetails;
import com.julfiker.admin.repository.ItemDetailsRepository;
import com.julfiker.admin.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemDetailsManagerImpl implements ItemDetailsManager{

    @Autowired
    ItemDetailsRepository itemDetailsRepository;
    @Autowired
    ItemRepository itemRepository;
    @Override
    public ItemDetailsDTO convertToDTO(ItemDetails details){
        ItemDetailsDTO detailsDTO =  new ItemDetailsDTO();
        detailsDTO.setItemDetailsID(details.getItemDetailsID());
        detailsDTO.setDescription(details.getDescription());
        detailsDTO.setItemID(details.getItem().getItemID());
        detailsDTO.setCreation_date(details.getCreation_date());
        if(details.getLast_updated() != null)
            detailsDTO.setLast_updated(details.getLast_updated());
        return detailsDTO;
    }

    @Override
    public void saveItemDetails(ItemDetailsDTO detailsDTO) {
        if(detailsDTO.getItemID() == null || detailsDTO.getDescription() == null){
            System.out.println("Not enough info to make item details");
            return;
        }
        ItemDetails details = itemDetailsRepository.findByItemDetailsID(detailsDTO.getItemDetailsID());
        if(details == null)
            details = new ItemDetails();
        else
            details.setLast_updated(LocalDateTime.now());
        details.setCreation_date(LocalDateTime.now());
        details.setItem(itemRepository.findByItemID(detailsDTO.getItemID()));
        details.setDescription(detailsDTO.getDescription());
        itemDetailsRepository.save(details);
    }

    @Override
    public void saveMultipleItemDetails(List<ItemDetailsDTO> detailsDTOS) {
        for(ItemDetailsDTO dto : detailsDTOS)
            this.saveItemDetails(dto);
    }

    @Override
    public ItemDetailsDTO findByID(Long ID) {
        return this.convertToDTO(itemDetailsRepository.findByItemDetailsID(ID));
    }

    @Override
    public List<ItemDetailsDTO> findAllByItemID(Long ID) {
        List<ItemDetails> details = itemDetailsRepository.findAllByItem_ItemID(ID);
        List<ItemDetailsDTO> detailsDTOS = new ArrayList<>();
        for(ItemDetails detail: details)
            detailsDTOS.add(this.convertToDTO(detail));
        return detailsDTOS;
    }

    @Override
    public List<ItemDetailsDTO> findAll() {
        List<ItemDetails> details = itemDetailsRepository.findAll();
        List<ItemDetailsDTO> detailsDTOS = new ArrayList<>();
        for(ItemDetails detail: details)
            detailsDTOS.add(this.convertToDTO(detail));
        return detailsDTOS;
    }

    @Override
    public void deleteItemDetails(Long ID) {
        itemDetailsRepository.deleteByItemDetailsID(ID);
    }

    @Override
    public void deleteAllItemsDetailsByItemID(Long ID) {
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("Could not find any item with this ID");
            return;
        }
        item.setItemDetails(null);
        itemDetailsRepository.deleteAllByItem_ItemID(ID);
    }

    @Override
    public void updateItemDetails(Long ID, ItemDetailsDTO detailsDTO) {
        ItemDetails itemDetails = itemDetailsRepository.findByItemDetailsID(ID);
        if(itemDetails == null){
            System.out.println("Could not find any item details with this ID");
            return;
        }
        if(detailsDTO.getDescription() != null) {
            itemDetails.setDescription(detailsDTO.getDescription());
            itemDetails.setLast_updated(LocalDateTime.now());
            itemDetailsRepository.save(itemDetails);
        }
    }
}

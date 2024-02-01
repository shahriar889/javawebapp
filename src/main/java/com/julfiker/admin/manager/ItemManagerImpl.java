package com.julfiker.admin.manager;

import com.julfiker.admin.dto.AttributeDTO;
import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.dto.ItemDTO;
import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.entity.*;
import com.julfiker.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemManagerImpl implements ItemManager{

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ItemTypeRepository itemTypeRepository;
    @Autowired
    AttributeManager attributeManager;
    @Autowired
    MediaManager mediaManager;
    @Autowired
    CategoryManager categoryManager;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    MediaRepository mediaRepository;

    private final Long defaultNumRating = 0L;
    private final BigDecimal defaultRating = BigDecimal.valueOf(0.0);


    public ItemDTO convertToDTO(Item item){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemID(item.getItemID());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setCreation_date(item.getCreation_date());
        itemDTO.setNum_ratings(item.getNum_ratings());
        itemDTO.setRating(item.getRating());
        itemDTO.setStock_quantity(item.getStock_quantity());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setSellerID(item.getSeller().getSellerID());
        if(item.getLast_updated() != null)
            itemDTO.setLast_updated(item.getLast_updated());
        itemDTO.setItemTypeID(item.getItemType().getItemTypeID());
        Set<Long> categoryIDs = new HashSet<>();
        Set<Category> categories = item.getCategories();
        for(Category category : categories)
            categoryIDs.add(category.getCategoryID());
        itemDTO.setCategoryIDs(categoryIDs);
        Set<Long> attributeIDs = new HashSet<>();
        Set<Attribute> attributes = item.getAttributes();
        for(Attribute attribute :attributes)
            attributeIDs.add(attribute.getAttributeID());
        itemDTO.setAttributeIDs(attributeIDs);
        Set<Media> mediaSet = item.getMedias();
        Set<Long> mediaIDs = new HashSet<>();
        for(Media media : mediaSet)
            mediaIDs.add(media.getMediaID());
        itemDTO.setMediaIDs(mediaIDs);
        List<CartItem> cartItems = item.getCartItems();
        List<Long> cartItemIDs = new ArrayList<>();
        for(CartItem cartItem : cartItems)
            cartItemIDs.add(cartItem.getCartItemID());
        itemDTO.setCartItemIDs(cartItemIDs);
        List<OrderItem> orderItems = item.getOrderItems();
        List<Long> orderItemIDs = new ArrayList<>();
        for(OrderItem orderItem : orderItems)
            orderItemIDs.add(orderItem.getOrderItemID());
        itemDTO.setOrderItemIDs(orderItemIDs);
        return itemDTO;
    }

    @Override
    public void saveItem(ItemDTO itemDTO) {
        if(itemDTO.getName() == null || itemDTO.getDescription() == null ||
        itemDTO.getItemTypeID() == null || itemDTO.getPrice() == null ||
        itemDTO.getSellerID() == null || itemDTO.getStock_quantity() == null){
            System.out.println("Do no have enough info to make Item");
            return;
        }
        Seller seller = sellerRepository.findBySellerID(itemDTO.getSellerID());
        ItemType itemType = itemTypeRepository.findByItemTypeID(itemDTO.getItemTypeID());
        if( seller == null){
            System.out.println("Could not find the seller");
            return;
        }
        if(itemType == null){
            System.out.println("Could not find the itemType");
            return;
        }
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setCreation_date(LocalDateTime.now());
        item.setPrice(itemDTO.getPrice());
        item.setNum_ratings(defaultNumRating);
        item.setRating(defaultRating);
        item.setStock_quantity(itemDTO.getStock_quantity());
        item.setSeller(seller);
        item.setItemType(itemType);
        seller.getItems().add(item);
        itemType.getItems().add(item);
        itemRepository.save(item);
        itemTypeRepository.save(itemType);
        sellerRepository.save(seller);
    }

    @Override
    public void updateItem(Long ID, ItemDTO itemDTO) {
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("Could not find Item with the ID");
            return;
        }
        if(itemDTO.getName() != null)
            item.setName(itemDTO.getName());
        if (itemDTO.getDescription() != null)
            item.setDescription(itemDTO.getDescription());
        if(itemDTO.getPrice() != null)
            item.setPrice(itemDTO.getPrice());
        if(itemDTO.getStock_quantity() != null)
            item.setStock_quantity(itemDTO.getStock_quantity());
        item.setLast_updated(LocalDateTime.now());
        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteItem(Long ID) {
        itemRepository.deleteById(ID);
    }

    @Override
    public ItemDTO findItemByID(Long ID) {
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("Could not find Item with the ID");
            return new ItemDTO();
        }
        return convertToDTO(item);
    }
    @Override
    public List<ItemDTO> findAll(){
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for(Item item : items)
            itemDTOS.add(convertToDTO(item));
        return itemDTOS;
    }

    @Override
    public Set<AttributeDTO> getAllItemAttributes(Long ID) {
        Item item = itemRepository.findByItemID(ID);
        Set<AttributeDTO> attributeDTOS = new HashSet<>();
        if(item == null){
            System.out.println("Could not find Item with the ID");
            return attributeDTOS;
        }
        Set<Attribute> attributes = item.getAttributes();
        for(Attribute attribute : attributes)
            attributeDTOS.add(attributeManager.convertToDTO(attribute));
        return attributeDTOS;
    }

    @Override
    public Set<MediaDTO> getAllItemMedias(Long ID){
        Item item = itemRepository.findByItemID(ID);
        Set<MediaDTO> mediaDTOS = new HashSet<>();
        if(item == null){
            System.out.println("Could not find Item with the ID");
            return mediaDTOS;
        }
       Set<Media> mediaSet = item.getMedias();
       for(Media media : mediaSet)
           mediaDTOS.add(mediaManager.convertToDTO(media));
       return mediaDTOS;
    }

    @Override
    public void setRatingToItem(Long ID, BigDecimal rating){
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("No item found associated with this ID");
            return;
        }
        Long curNumRatings = item.getNum_ratings();
        BigDecimal curTotalRating = item .getRating().multiply(BigDecimal.valueOf(curNumRatings));
        BigDecimal newTotalRating = curTotalRating.add(rating);
        BigDecimal newNumRatings = BigDecimal.valueOf(curNumRatings + 1);
        BigDecimal newRating = newTotalRating.divide(newNumRatings, RoundingMode.HALF_UP);
        item.setRating(newRating);
        item.setNum_ratings(curNumRatings+1L);
        item.setLast_updated(LocalDateTime.now());
        itemRepository.save(item );
    }

    @Override
    public void addCategoryToItem(Long ID, Long ID2) {
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("No item found associated with this ID");
            return;
        }
        Category category = categoryRepository.findByCategoryID(ID2);
        if(category == null){
            System.out.println("No category found associated with this ID");
            return;
        }
        item.getCategories().add(category);
        category.getItems().add(item);
        itemRepository.save(item);
        categoryRepository.save(category);
    }

    @Override
    public Set<CategoryDto> getAllItemCategories(Long ID){
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("No item found associated with this ID");
            return new HashSet<>();
        }
        Set<CategoryDto> categoryDTOs = new HashSet<>();
        Set<Category> categories = item.getCategories();
        for(Category category : categories)
            categoryDTOs.add(categoryManager.convertToDTO(category));
        return categoryDTOs;
    }

    @Override
    public void addAttributeToItem(Long ID, Long ID2){
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("No item found associated with this ID");
            return;
        }
        Attribute attribute = attributeRepository.findByAttributeID(ID2);
        if(attribute == null){
            System.out.println("No attribute found associated with this ID");
            return;
        }
        item.getAttributes().add(attribute);
        attribute.getItems().add(item);
        itemRepository.save(item);
        attributeRepository.save(attribute);
    }


    @Override
    public void addMediaToItem(Long ID, Long ID2){
        Item item = itemRepository.findByItemID(ID);
        if(item == null){
            System.out.println("No item found associated with this ID");
            return;
        }
        Media media = mediaRepository.findByMediaID(ID2);
        if(media == null){
            System.out.println("No media found associated with this ID");
            return;
        }
        item.getMedias().add(media);
        media.getItems().add(item);
        itemRepository.save(item);
        mediaRepository.save(media);

    }
}

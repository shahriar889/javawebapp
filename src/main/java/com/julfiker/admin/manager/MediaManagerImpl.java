package com.julfiker.admin.manager;

import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.entity.Customer;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.entity.Media;
import com.julfiker.admin.entity.Seller;
import com.julfiker.admin.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaManagerImpl implements MediaManager{

    @Autowired
    private MediaRepository mediaRepository;

    private MediaDTO convertToDTO(Media media){
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setMediaID(media.getMediaID());
        mediaDTO.setFileOriginalPath(media.getFileOriginalPath());
        mediaDTO.setFileExtension(media.getFileExtension());
        mediaDTO.setFileThumbnailPath(media.getFileThumbnailPath());
        mediaDTO.setFileSize(media.getFileSize());
        mediaDTO.setCreation_date(media.getCreation_date());
        if(media.getLast_updated() != null)
            mediaDTO.setLast_updated(media.getLast_updated());
        List<Item> items = media.getItems();
        List<Long> itemIDList = new ArrayList<>();
        for (Item item : items) {
            itemIDList.add(item.getItemID());
        }
        mediaDTO.setItemIDList(itemIDList);
        if(media.getCustomer() != null)
            mediaDTO.setCustomerID(media.getCustomer().getCustomerID());
        if(media.getSeller() != null)
            mediaDTO.setSellerID(media.getSeller().getSellerID());
        return mediaDTO;
    }

    @Override
    public void saveMedia(MediaDTO mediaDTO){
        if(mediaDTO.getFileExtension() == null || mediaDTO.getFileSize() == null||
        mediaDTO.getFileOriginalPath() == null || mediaDTO.getFileThumbnailPath() == null){
            System.out.println("These colums cannot be null.");
            return;
        }
        Media media = new Media();
        media.setFileExtension(mediaDTO.getFileExtension());
        media.setFileOriginalPath(mediaDTO.getFileOriginalPath());
        media.setFileThumbnailPath(mediaDTO.getFileThumbnailPath());
        media.setFileSize(mediaDTO.getFileSize());
        List<Item> items = new ArrayList<>();
        media.setItems(items);
//        Customer customer = new Customer();
//        Seller seller = new Seller();
//        media.setCustomer(customer);
//        media.setSeller(seller);
        if(media.getCreation_date() == null)
            media.setCreation_date(LocalDateTime.now());
        mediaRepository.save(media);

    }

    @Override
    public void updateMedia(MediaDTO mediaDTO){
        if(mediaDTO.getMediaID() == null){
            System.out.println("Need media ID to find Media to update");
            return;
        }
        Media media = mediaRepository.findByMediaID(mediaDTO.getMediaID());
        if(media == null){
            System.out.println("Could not find the media associated with the given ID");
            return;
        }
        if (mediaDTO.getFileThumbnailPath() != null)
            media.setFileThumbnailPath(mediaDTO.getFileThumbnailPath());
        if(mediaDTO.getFileOriginalPath() != null)
            media.setFileOriginalPath(mediaDTO.getFileOriginalPath());
        if(mediaDTO.getFileSize() != null)
            media.setFileSize(mediaDTO.getFileSize());
        if(mediaDTO.getFileExtension() != null)
            media.setFileExtension(mediaDTO.getFileExtension());
        media.setLast_updated(LocalDateTime.now());
        mediaRepository.save(media);
    }

    @Override
    public MediaDTO findMediaByID(Long ID){

        Media media = mediaRepository.findByMediaID(ID);
        if(media == null){
            System.out.println("Could not find find media with this ID");
            return new MediaDTO();
        }
        return convertToDTO(media);
    }

    @Override
    public List<MediaDTO> findAllMedia(){

        List<Media> mediaList = mediaRepository.findAll();
        List<MediaDTO> mediaDTOList = new ArrayList<>();
        for(Media media : mediaList){
            mediaDTOList.add(convertToDTO(media));
        }
        return mediaDTOList;
    }

    @Override
    public MediaDTO findMediaByFileOriginalPath(String msg){
        Media media = mediaRepository.findByFileOriginalPath(msg);
        if(media == null){
            System.out.println("Could not find find media with this OG Path");
            return new MediaDTO();
        }
        return convertToDTO(media);
    }

    @Override
    public MediaDTO findMediaByFileThumbnailPath(String msg){
        Media media = mediaRepository.findByFileThumbnailPath(msg);
        if(media == null){
            System.out.println("Could not find find media with this Thumbnail Path");
            return new MediaDTO();
        }
        return convertToDTO(media);
    }

    @Override
    public List<MediaDTO> findAllMediaByFileExtension(String msg){
        List<Media> mediaList = mediaRepository.findAllByFileExtension(msg);
        List<MediaDTO> mediaDTOList = new ArrayList<>();
        for(Media media : mediaList){
            mediaDTOList.add(convertToDTO(media));
        }
        return mediaDTOList;
    }

    @Override
    public void deleteMediaByID(Long ID){
        mediaRepository.deleteById(ID);
    }
}

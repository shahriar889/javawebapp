package com.julfiker.admin.manager;

import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.entity.Media;

import java.util.List;

public interface MediaManager {
    void saveMedia(MediaDTO mediaDTO);
    MediaDTO findMediaByID(Long ID);

    List<MediaDTO> findAllMedia();

    void updateMedia(MediaDTO mediaDTO, Long ID);
    void deleteMediaByID(Long ID);
    MediaDTO convertToDTO(Media media);

    Long getMediaID(String msg);

}

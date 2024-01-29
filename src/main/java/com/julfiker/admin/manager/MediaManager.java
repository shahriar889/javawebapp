package com.julfiker.admin.manager;

import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.entity.Media;

import java.util.List;

public interface MediaManager {
    void saveMedia(MediaDTO mediaDTO);
    MediaDTO findMediaByID(Long ID);
    MediaDTO findMediaByFileOriginalPath(String msg);
    MediaDTO findMediaByFileThumbnailPath(String msg);

    List<MediaDTO> findAllMediaByFileExtension(String msg);

    List<MediaDTO> findAllMedia();

    void updateMedia(MediaDTO mediaDTO);
    void deleteMediaByID(Long ID);

}

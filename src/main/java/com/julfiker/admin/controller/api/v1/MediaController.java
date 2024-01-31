package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.manager.MediaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MediaController {

    @Autowired
    MediaManager mediaManager;

    @GetMapping("/medias")
    public List<MediaDTO> getAllMedia(@RequestParam(required = false) String OGPath,
                                      @RequestParam(required = false) String thumbPath,
                                      @RequestParam(required = false) String fileExt) {
        return mediaManager.findAllMedia().stream()
                .filter(mediaDTO ->
                        (OGPath == null || mediaDTO.getFileOriginalPath().equals(OGPath)) &&
                                (thumbPath == null || mediaDTO.getFileThumbnailPath().equals(thumbPath)) &&
                                (fileExt == null || mediaDTO.getFileExtension().equals(fileExt)))
                .collect(Collectors.toList());
    }

    @GetMapping("/medias/{ID}")
    public MediaDTO getMediaByID(@PathVariable Long ID) {
        return mediaManager.findMediaByID(ID);
    }

    @PostMapping("/medias")
    public void createMedia(@RequestBody MediaDTO mediaDTO) {
        mediaManager.saveMedia(mediaDTO);
    }

    @PutMapping("/medias/{ID}")
    public void updateMedia(@PathVariable Long ID, @RequestBody MediaDTO mediaDTO) {
        mediaManager.updateMedia(mediaDTO, ID);
    }

    @DeleteMapping("/medias/{ID}")
    @Transactional
    public void deleteMedia(@PathVariable Long ID) {
        mediaManager.deleteMediaByID(ID);
    }

}

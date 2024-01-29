package com.julfiker.admin.api.v1;

import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.entity.Media;
import com.julfiker.admin.manager.MediaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    @Autowired
    MediaManager mediaManager;

    @GetMapping("/getAll")
    public List<MediaDTO> getAllMedia(){
       return mediaManager.findAllMedia();
    }
    @GetMapping("/getByID")
    public MediaDTO getMediaByID(@RequestParam Long ID){
        return mediaManager.findMediaByID(ID);
    }
    @GetMapping("/getByOriginalPath")
    public MediaDTO getMediaByOriginalPath(@RequestParam String msg){
        return mediaManager.findMediaByFileOriginalPath(msg);
    }
    @GetMapping("/getByThumbnailPath")
    public MediaDTO getMediaByThumbnailPath(@RequestParam String msg){
        return mediaManager.findMediaByFileThumbnailPath(msg);
    }
    @GetMapping("/getByAllByFileExtension")
    public List<MediaDTO> getAllMediaByFileExtension(@RequestParam String msg){
        return mediaManager.findAllMediaByFileExtension(msg);
    }
    @PostMapping("/create")
    public void createMedia(@RequestBody MediaDTO mediaDTO){
        mediaManager.saveMedia(mediaDTO);
    }
    @PutMapping("/update")
    public void updateMedia(@RequestBody MediaDTO mediaDTO){
        mediaManager.updateMedia(mediaDTO);
    }

    @DeleteMapping("/delete")
    @Transactional
    public void deleteMedia(@RequestParam Long ID){
        mediaManager.deleteMediaByID(ID);
    }

}

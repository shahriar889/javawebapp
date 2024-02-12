package com.julfiker.admin.controller.backOffice;

import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.manager.MediaManager;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ControllerHelpers {

    @Value("${upload.dir}")
    private String uploadDir;
    @Autowired
    MediaManager mediaManager;
    public Long saveImages(MultipartFile[] files){
        Long savedMediaID = 0L;
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    // Skip empty files
                    continue;
                }


                try {
                    // Save the original file
                    String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
                    String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
                    File destDir = new File(uploadDir);
                    if (!destDir.exists()) {
                        destDir.mkdirs();
                    }
                    MediaDTO mediaDTO = new MediaDTO();
                    File destFile = new File(destDir.getAbsolutePath() + File.separator + fileName);
                    file.transferTo(destFile);
                    mediaDTO.setFileOriginalPath("/assets/images/" + fileName);
                    mediaDTO.setFileSize((double) file.getSize());
                    int lastIndex = originalFilename.lastIndexOf('.');
                    if (lastIndex >= 0 && lastIndex < originalFilename.length() - 1) {
                        String fileExtension = originalFilename.substring(lastIndex);
                        mediaDTO.setFileExtension(fileExtension);
                    } else {
                        System.out.println("No file extension found.");
                    }


                    // Generate thumbnail
                    BufferedImage originalImage = ImageIO.read(destFile);
                    int thumbnailWidth = 250; // Specify your desired width
                    int thumbnailHeight = 200; // Specify your desired height
                    BufferedImage thumbnail = Thumbnails.of(originalImage)
                            .size(thumbnailWidth, thumbnailHeight)
                            .asBufferedImage();

                    // Save the thumbnail
                    String thumbnailFileName = "thumbnail_" + fileName;
                    File thumbnailFile = new File(destDir.getAbsolutePath() + File.separator + thumbnailFileName);
                    ImageIO.write(thumbnail, "jpg", thumbnailFile);
                    mediaDTO.setFileThumbnailPath("/assets/images/" + thumbnailFileName);
                    mediaManager.saveMedia(mediaDTO);
                    savedMediaID = mediaManager.getMediaID(mediaDTO.getFileOriginalPath());


                    // Optionally, you can save the file paths to the database or do other processing
                } catch (IOException e) {
                    // Handle file processing errors
                    e.printStackTrace();
                }
            }
        }
        return savedMediaID;
    }
}

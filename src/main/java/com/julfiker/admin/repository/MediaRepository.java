package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAll();
    Media findByMediaID(Long MediaID);
    Media findByFileOriginalPath(String msg);
    List<Media> findAllByFileExtension(String msg);
    Media findByFileThumbnailPath(String msg);

    void deleteByMediaID(Long ID);


}

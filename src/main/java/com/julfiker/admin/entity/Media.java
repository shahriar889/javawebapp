package com.julfiker.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medias")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Media_ID")
    private long mediaID;

    @Column(nullable = false)
    private String fileOriginalPath;

    @Column(nullable = false)
    private String fileThumbnailPath;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private double fileSize;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;

    @ManyToMany(mappedBy="medias")
    private List<Item> items;

    @OneToOne(mappedBy = "media", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Customer customer;

    @OneToOne(mappedBy = "media", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Seller seller;



}

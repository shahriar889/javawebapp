package com.julfiker.admin.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Item_ID")
    private Long itemID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal rating;

    @Column(nullable = false)
    private long num_ratings;

    @Column(nullable = false)
    private long stock_quantity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;

    @ManyToOne
    @JoinColumn(name = "Seller_ID")
    private Seller seller;



    @ManyToOne
    @JoinColumn(name = "Item_type_ID", nullable = false)
    private ItemType itemType;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_categories",
            joinColumns = @JoinColumn(name = "Item_ID"),
            inverseJoinColumns = @JoinColumn(name = "Category_ID")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_attributes",
            joinColumns = @JoinColumn(name = "Item_ID"),
            inverseJoinColumns = @JoinColumn(name = "Attribute_ID")
    )
    private Set<Attribute> attributes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_medias",
            joinColumns = @JoinColumn(name = "Item_ID"),
            inverseJoinColumns = @JoinColumn(name = "Media_ID")
    )
    private Set<Media> medias = new HashSet<>();

    @OneToOne(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private CartItem cartItem;

    @OneToOne(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private OrderItem orderItem;

}

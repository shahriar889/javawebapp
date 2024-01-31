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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_ID")
    private Long categoryID;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "Parent_ID")
    private Category parentCategory;

    @Column
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;

    @Column
    private LocalDateTime perishable;

    @ManyToMany(mappedBy="categories")
    private List<Item> items;



}

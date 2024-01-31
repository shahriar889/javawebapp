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
@Table(name = "attributes_types")
public class Attributes_Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Attributes_Type_ID")
    private Long attributesTypeID;

    @Column(nullable = false, unique = true)
    private String name;


    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;

    @OneToMany(mappedBy = "attributesType")
    List<Attribute> attributes;
}

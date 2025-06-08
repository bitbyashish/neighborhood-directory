package com.bitbyashish.neighborhood_service_directory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceProvider extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String city;
    private String phone;

    private Double averageRating;
    private Long reviewCount;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
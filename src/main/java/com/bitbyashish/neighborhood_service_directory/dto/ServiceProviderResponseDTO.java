package com.bitbyashish.neighborhood_service_directory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderResponseDTO {
    private Long id;
    private String name;
    private String category;
    private String city;
    private String phone;
    private Double averageRating;
    private Long reviewCount;
}

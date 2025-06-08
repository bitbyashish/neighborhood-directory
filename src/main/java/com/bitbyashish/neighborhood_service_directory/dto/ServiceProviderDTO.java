package com.bitbyashish.neighborhood_service_directory.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class ServiceProviderDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private String city;

    @NotBlank
    @Size(min =10, max = 10)
    private String phone;

    private Double averageRating;
    private Long reviewCount;
}

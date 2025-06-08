package com.bitbyashish.neighborhood_service_directory.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class ReviewCreateDTO {
    @Min(1) @Max(5)
    private int rating;

    @NotBlank
    private String comment;

    private Long serviceProviderId;
}

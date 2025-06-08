package com.bitbyashish.neighborhood_service_directory.controller;

import com.bitbyashish.neighborhood_service_directory.dto.ReviewCreateDTO;
import com.bitbyashish.neighborhood_service_directory.dto.ReviewResponseDTO;
import com.bitbyashish.neighborhood_service_directory.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ReviewResponseDTO create(@Valid @RequestBody ReviewCreateDTO dto) {
        return reviewService.createReview(dto);
    }

    @GetMapping("/provider/{providerId}")
    public List<ReviewResponseDTO> getByProvider(@PathVariable Long providerId) {
        return reviewService.getReviewsForProvider(providerId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> softDeleteReview(@PathVariable Long id) {
        reviewService.softDeleteReview(id);
        return ResponseEntity.noContent().build();
    }
}

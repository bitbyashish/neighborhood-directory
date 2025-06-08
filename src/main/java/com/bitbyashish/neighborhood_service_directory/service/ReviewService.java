package com.bitbyashish.neighborhood_service_directory.service;

import com.bitbyashish.neighborhood_service_directory.dto.ReviewCreateDTO;
import com.bitbyashish.neighborhood_service_directory.dto.ReviewResponseDTO;
import com.bitbyashish.neighborhood_service_directory.entity.Review;
import com.bitbyashish.neighborhood_service_directory.entity.ServiceProvider;
import com.bitbyashish.neighborhood_service_directory.repository.ReviewRepository;
import com.bitbyashish.neighborhood_service_directory.repository.ServiceProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ServiceProviderRepository providerRepo;

    @Autowired
    private ModelMapper mapper;

    public ReviewResponseDTO createReview(ReviewCreateDTO dto) {
        ServiceProvider sp = providerRepo.findById(dto.getServiceProviderId())
                .orElseThrow(() -> new RuntimeException("Service Provider not found"));

        Review review = mapper.map(dto, Review.class);
        review.setServiceProvider(sp);
        review.setActive(true);
        reviewRepo.save(review);

        updateRatingAndCount(sp);

        return mapper.map(review, ReviewResponseDTO.class);
    }

    public List<ReviewResponseDTO> getReviewsForProvider(Long providerId) {
        return reviewRepo.findByServiceProviderIdAndActiveTrue(providerId).stream()
                .map(r -> mapper.map(r, ReviewResponseDTO.class))
                .collect(Collectors.toList());
    }

    private void updateRatingAndCount(ServiceProvider sp) {
    Long count = reviewRepo.countByServiceProviderId(sp.getId());
    Double avg = reviewRepo.findAverageRatingByServiceProviderId(sp.getId());
    if (avg == null) {
        avg = 0.0;
    }

    sp.setReviewCount(count);
    sp.setAverageRating(avg);
    providerRepo.save(sp);
}


    @Transactional
    public void softDeleteReview(Long id) {
        Review review = reviewRepo.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setActive(false);
        reviewRepo.save(review);
    }
}

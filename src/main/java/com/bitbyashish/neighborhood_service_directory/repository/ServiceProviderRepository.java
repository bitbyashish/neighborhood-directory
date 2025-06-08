package com.bitbyashish.neighborhood_service_directory.repository;

import com.bitbyashish.neighborhood_service_directory.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    Page<ServiceProvider> findByActiveTrueAndCityIgnoreCaseAndCategoryIgnoreCase(
        String city, String category, Pageable pageable
    );

    Page<ServiceProvider> findByActiveTrueAndAverageRatingBetween(
        double minRating, double maxRating, Pageable pageable
    );

    Page<ServiceProvider> findByActiveTrue(Pageable pageable);

    Optional<ServiceProvider> findByIdAndActiveTrue(Long id);
}
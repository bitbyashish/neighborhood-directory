package com.bitbyashish.neighborhood_service_directory.repository;

import com.bitbyashish.neighborhood_service_directory.entity.Review;
import com.bitbyashish.neighborhood_service_directory.entity.ServiceProvider;
import com.bitbyashish.neighborhood_service_directory.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByServiceProviderIdAndActiveTrue(Long serviceProviderId);

    Optional<Review> findByIdAndActiveTrue(Long id);

    List<Review> findByServiceProviderAndActiveTrue(ServiceProvider serviceProvider);

    List<Review> findByUserAndActiveTrue(User user);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.serviceProvider.id = :serviceProviderId AND r.active = true")
    Double findAverageRatingByServiceProviderId(@Param("serviceProviderId") Long serviceProviderId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.serviceProvider.id = :serviceProviderId AND r.active = true")
    Long countByServiceProviderId(@Param("serviceProviderId") Long serviceProviderId);
}

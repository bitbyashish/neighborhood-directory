package com.bitbyashish.neighborhood_service_directory.service;

import com.bitbyashish.neighborhood_service_directory.dto.*;
import com.bitbyashish.neighborhood_service_directory.entity.ServiceProvider;
import com.bitbyashish.neighborhood_service_directory.repository.ReviewRepository;
import com.bitbyashish.neighborhood_service_directory.repository.ServiceProviderRepository;

import org.springframework.transaction.annotation.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository providerRepo;

    @Autowired
    private ReviewRepository reviewRepo;


    @Autowired
    private ModelMapper modelMapper;

    public ServiceProviderResponseDTO create(ServiceProviderDTO dto) {
        ServiceProvider sp = modelMapper.map(dto, ServiceProvider.class);
        sp.setActive(true);
        providerRepo.save(sp);
        return modelMapper.map(sp, ServiceProviderResponseDTO.class);
    }

    public void softDelete(Long id) {
        ServiceProvider sp = providerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        sp.setActive(false);
        providerRepo.save(sp);
    }
    
    public List<ServiceProviderResponseDTO> getAll() {
        List<ServiceProvider> providers = providerRepo.findAll().stream()
                .filter(ServiceProvider::isActive)
                .collect(Collectors.toList());

        providers.forEach(sp -> {
            Double avg = reviewRepo.findAverageRatingByServiceProviderId(sp.getId());
            Long count = reviewRepo.countByServiceProviderId(sp.getId());

            sp.setAverageRating(avg != null ? avg : 0.0);
            sp.setReviewCount(count != null ? count : 0L);
        });

        return providers.stream()
                .map(sp -> modelMapper.map(sp, ServiceProviderResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Update filter() similarly:
    public Page<ServiceProviderResponseDTO> filter(String city, String category, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<ServiceProvider> providers = providerRepo
            .findByActiveTrueAndCityIgnoreCaseAndCategoryIgnoreCase(city, category, pageable);

        providers.forEach(sp -> {
            Double avg = reviewRepo.findAverageRatingByServiceProviderId(sp.getId());
            Long count = reviewRepo.countByServiceProviderId(sp.getId());

            sp.setAverageRating(avg != null ? avg : 0.0);
            sp.setReviewCount(count != null ? count : 0L);
        });

        return providers.map(sp -> modelMapper.map(sp, ServiceProviderResponseDTO.class));
    }

    // Update filterByRating() similarly:
    public Page<ServiceProviderResponseDTO> filterByRating(double min, double max, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<ServiceProvider> providers = providerRepo
            .findByActiveTrueAndAverageRatingBetween(min, max, pageable);

        providers.forEach(sp -> {
            Double avg = reviewRepo.findAverageRatingByServiceProviderId(sp.getId());
            Long count = reviewRepo.countByServiceProviderId(sp.getId());

            sp.setAverageRating(avg != null ? avg : 0.0);
            sp.setReviewCount(count != null ? count : 0L);
        });

        return providers.map(sp -> modelMapper.map(sp, ServiceProviderResponseDTO.class));
    }

    @Transactional
    public void softDeleteServiceProvider(Long id) {
        ServiceProvider sp = providerRepo.findByIdAndActiveTrue(id)
            .orElseThrow(() -> new RuntimeException("Service Provider not found"));
        sp.setActive(false);
        providerRepo.save(sp);
    }
}
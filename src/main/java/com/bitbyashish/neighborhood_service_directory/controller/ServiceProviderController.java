package com.bitbyashish.neighborhood_service_directory.controller;

import com.bitbyashish.neighborhood_service_directory.dto.*;
import com.bitbyashish.neighborhood_service_directory.service.ServiceProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService service;

    @PostMapping
    public ServiceProviderResponseDTO create(@Valid @RequestBody ServiceProviderDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ServiceProviderResponseDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @GetMapping("/filter")
    public Page<ServiceProviderResponseDTO> filterByCityAndCategory(
            @RequestParam String city,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {
        return service.filter(city, category, page, size, sortBy);
    }

    @GetMapping("/filter/rating")
    public Page<ServiceProviderResponseDTO> filterByRating(
            @RequestParam double min,
            @RequestParam double max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "averageRating") String sortBy
    ) {
        return service.filterByRating(min, max, page, size, sortBy);
    }

    @DeleteMapping("/{id}/soft")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> softDeleteProvider(@PathVariable Long id) {
        service.softDeleteServiceProvider(id);
        return ResponseEntity.noContent().build();
    }

}
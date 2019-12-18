package com.example.restaurant.application;

import com.example.restaurant.domain.Region;
import com.example.restaurant.domain.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {

        return regionRepository.findAll();
    }

    public Region addRegion(String name) {
        return regionRepository.save(Region.builder().name(name).build());
    }
}

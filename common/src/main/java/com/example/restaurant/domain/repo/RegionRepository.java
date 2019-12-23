package com.example.restaurant.domain.repo;

import com.example.restaurant.domain.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepository extends CrudRepository<Region,Long> {
    List<Region> findAll();
}

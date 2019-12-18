package com.example.restaurant.interfaces;

import com.example.restaurant.application.RegionService;
import com.example.restaurant.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.any;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/regions")
    public List<Region> list(){
        List<Region> regions = regionService.getRegions();
        return regions;
    }

    @PostMapping("/regions")
    public ResponseEntity create(@RequestBody Region resource) throws URISyntaxException {
        Region region = regionService.addRegion(resource.getName());
        String url = "/regions/"+region.getId();
        return ResponseEntity.created(new URI(url)).body("");
    }
}

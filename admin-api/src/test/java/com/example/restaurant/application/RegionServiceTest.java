package com.example.restaurant.application;

import com.example.restaurant.domain.Region;
import com.example.restaurant.domain.RegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RegionServiceTest {

    private RegionService regionService;
    @Mock
    private RegionRepository regionRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository);

        regionRepositorySetUp();
    }

    private void regionRepositorySetUp(){
        List<Region> regions = new ArrayList<>();
        regions.add(Region.builder().name("서울").build());
        given(regionRepository.findAll()).willReturn(regions);
    }

    @Test
    public void getRegions(){
        List<Region> regions = regionService.getRegions();
        Region region = regions.get(0);
        assertThat(region.getName(),is("서울"));
    }

    @Test
    public void addRegion(){

        given(regionRepository.save(any())).willReturn(Region.builder().id(1L).name("서울").build());

        Region region = regionService.addRegion("서울");
        assertThat(region.getName(),is("서울"));
    }
}
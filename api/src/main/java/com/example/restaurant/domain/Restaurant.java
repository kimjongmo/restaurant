package com.example.restaurant.domain;

public class Restaurant {

    private Long id;
    private final String name;
    private final String address;

    public Restaurant(Long id,String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }


    public String getName() {
        return name;
    }


    public Long getId(){
        return id;
    }
    public String getAddress(){
        return address;
    }

    public String getInformation(){
        return name+" in "+address;
    }
}

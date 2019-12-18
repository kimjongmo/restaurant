package com.example.restaurant.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}

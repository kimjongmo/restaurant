package com.example.restaurant.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;
    private String name;

    @Max(5)
    @Min(0)
    @NotNull
    private Integer score;

    @NotEmpty
    private String description;

}

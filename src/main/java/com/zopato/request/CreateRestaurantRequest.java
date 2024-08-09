package com.zopato.request;

import java.time.LocalDateTime;
import java.util.List;

import com.zopato.model.Address;
import com.zopato.model.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private String openingHours;
    private Address address;
    private ContactInformation contactInformation;
    private List<String> images;

}

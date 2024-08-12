package com.zopato.request;

import com.zopato.model.Address;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private Long restaurantId;
    private Address DeliveryAddress;

}

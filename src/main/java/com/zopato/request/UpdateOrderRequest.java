package com.zopato.request;

import lombok.Data;

@Data
public class UpdateOrderRequest {

    private String orderStatus;
    private Long orderId;

}

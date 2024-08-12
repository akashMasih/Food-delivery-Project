package com.zopato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zopato.model.Order;
import com.zopato.model.User;
import com.zopato.service.OrderService;
import com.zopato.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.getUserByJwtToken(jwt);
        List<Order> orderHistory = orderService.getRestaurantOrders(user.getId(), order_status);
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        Order order = orderService.updateOrder(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}

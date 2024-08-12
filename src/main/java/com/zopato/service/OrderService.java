package com.zopato.service;

import java.util.List;

import com.zopato.model.Order;
import com.zopato.model.User;
import com.zopato.request.CreateOrderRequest;

public interface OrderService {

    public Order createOrder(CreateOrderRequest req, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

}

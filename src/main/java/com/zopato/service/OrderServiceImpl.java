package com.zopato.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zopato.model.Address;
import com.zopato.model.Cart;
import com.zopato.model.CartItem;
import com.zopato.model.Order;
import com.zopato.model.OrderItem;
import com.zopato.model.Restaurant;
import com.zopato.model.User;
import com.zopato.repository.AddressRepository;
import com.zopato.repository.OrderItemRepository;
import com.zopato.repository.OrderRepository;
import com.zopato.repository.UserRepository;
import com.zopato.request.CreateOrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(CreateOrderRequest req, User user) throws Exception {
        Address shipAddress = req.getDeliveryAddress();

        Address savAddress = addressRepository.save(shipAddress);

        if (!user.getAddresses().contains(savAddress)) {
            user.getAddresses().add(savAddress);
            user = userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Order createOrder = new Order();
        createOrder.setCustomer(user);
        createOrder.setDeliveryAddress(savAddress);
        createOrder.setCreatedAt(new Date());
        createOrder.setRestaurant(restaurant);
        createOrder.setOrderStatus("PENDING");

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        createOrder.setItems(orderItems);
        Long totalPrice = cartService.calculateCartTotals(cart);
        createOrder.setTotalAmount(totalPrice);

        Order savedOrder = orderRepository.save(createOrder);
        restaurant.getOrders().add(savedOrder);

        return savedOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (order.getOrderStatus().equals("OUT_FOR_DELIVERY") ||
                order.getOrderStatus().equals("DELIVERED") ||
                order.getOrderStatus().equals("COMPLETED") ||
                order.getOrderStatus().equals("PENDING")) {

            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        } else {
            throw new Exception("Please select a valid order status");

        }

    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {

        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {

        List<Order> orders = orderRepository.findByCustomer(userId);
        if (orders.isEmpty()) {
            throw new Exception("Order not found with this id " + userId);
        }
        return orders;

    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {

        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if (!orders.isEmpty()) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).toList();

        }
        return orders;

    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new Exception("Order not found with this id " + orderId);
        }
        return order.get();
    }

}

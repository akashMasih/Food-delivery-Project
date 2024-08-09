package com.zopato.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zopato.dto.RestaurantDto;
import com.zopato.model.Address;
import com.zopato.model.Restaurant;
import com.zopato.model.User;
import com.zopato.reposetory.AddressRepository;
import com.zopato.reposetory.RestaurantRepository;
import com.zopato.reposetory.UserRepository;
import com.zopato.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();

        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOwner(user);
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setAddress(address);
        restaurant.setImages(req.getImages());

        return restaurantRepository.save(restaurant);

    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest req) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (req.getName() != null) {
            restaurant.setName(req.getName());
        }

        if (req.getDescription() != null) {
            restaurant.setDescription(req.getDescription());
        }

        if (req.getCuisineType() != null) {
            restaurant.setCuisineType(req.getCuisineType());
        }

        if (req.getOpeningHours() != null) {
            restaurant.setOpeningHours(req.getOpeningHours());
        }

        if (req.getContactInformation() != null) {
            restaurant.setContactInformation(req.getContactInformation());
        }

        if (req.getImages() != null) {
            restaurant.setImages(req.getImages());
        }

        if (req.getAddress() != null) {
            Address address = addressRepository.save(req.getAddress());
            restaurant.setAddress(address);
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {

        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String query) {

        return restaurantRepository.findBySearchQuery(query);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);

        if (!opt.isPresent()) {
            throw new Exception("Restaurant not found with this id " + restaurantId);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with this user id " + userId);
        }
        return restaurant;

    }

    @Override
    public RestaurantDto addToFavorite(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setTitle(restaurant.getName());
        dto.setImages(restaurant.getImages());
        dto.setId(restaurantId);

        List<RestaurantDto> favorites = user.getFavorites();

        boolean isFavorite = false;
        for (RestaurantDto dt : favorites) {
            if (dt.getId().equals(restaurantId)) {
                isFavorite = true;
                break;
            }
        }
        if (isFavorite) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return dto;

    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }

}

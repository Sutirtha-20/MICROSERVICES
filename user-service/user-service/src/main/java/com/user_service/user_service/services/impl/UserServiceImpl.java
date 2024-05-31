package com.user_service.user_service.services.impl;

import com.user_service.user_service.entity.Hotel;
import com.user_service.user_service.entity.Rating;
import com.user_service.user_service.entity.User;
import com.user_service.user_service.exception.ResourceNotFoundException;
import com.user_service.user_service.repository.UserRepository;
import com.user_service.user_service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.module.ResolutionException;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        //received user from user repo
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id isn't found on server" + userId));
        //fetch rating of above user from rating service
        //http://localhost:8083/ratings/users/71b23895-9e5f-4670-9fab-adb6bbdec226
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList(); 
        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service
            //localhost:8082/hotels/e60c01d5-c099-4e01-b1af-29fb93e1e129
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status code"+forEntity.getStatusCode());
            //set the hotel to rating array
            rating.setHotel(hotel);
            //return rating
            return rating;
        }).collect(Collectors.toList());

        //at this set hotel info are included in rating info
        user.setRatings(ratingList);
        return user;
    }
}

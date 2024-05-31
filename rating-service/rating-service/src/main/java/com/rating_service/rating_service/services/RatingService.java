package com.rating_service.rating_service.services;

import com.rating_service.rating_service.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    public Rating createRating(Rating rating);

    public List<Rating> getAllRating();

    public List<Rating> getAllRatingsByUserId(String userId);

    public List<Rating> getAllRatingByHotelId(String hotelId);
}

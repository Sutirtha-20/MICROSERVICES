package com.hotel_service.hotel_service.services;

import com.hotel_service.hotel_service.entity.Hotel;

import java.util.List;

public interface HotelService {

    public Hotel createHotel(Hotel hotel);

    public List<Hotel> getAllHotel();

    public Hotel getHotelById(String id);
}

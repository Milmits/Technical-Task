package com.example.demo.service;

import com.example.demo.model.Hotel;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(int id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public List<Hotel> searchHotels(String name, String brand, String city, String county, String amenity) {
        if (name != null) {
            return hotelRepository.findByNameContainingIgnoreCase(name);
        } else if (brand != null) {
            return hotelRepository.findByBrandContainingIgnoreCase(brand);
        } else if (city != null) {
            return hotelRepository.findByAddress_CityContainingIgnoreCase(city);
        } else if (county != null) {
            return hotelRepository.findByAddress_CountyContainingIgnoreCase(county);
        } else if (amenity != null) {
            return hotelRepository.findByAmenitiesContainingIgnoreCase(amenity);
        }
        return hotelRepository.findAll();
    }

    public Hotel addAmenities(int hotelId, Set<String> amenities) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        if (hotelOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();
            hotel.getAmenities().addAll(amenities);
            return hotelRepository.save(hotel);
        }
        return null;
    }

    public Map<String, Long> getHistogram(String param) {
        List<Hotel> hotels = hotelRepository.findAll();
        Map<String, Long> histogram = new HashMap<>();

        switch (param.toLowerCase()) {
            case "brand":
                hotels.forEach(hotel -> histogram.merge(hotel.getBrand(), 1L, Long::sum));
                break;
            case "city":
                hotels.forEach(hotel -> histogram.merge(hotel.getAddress().getCity(), 1L, Long::sum));
                break;
            case "county":
                hotels.forEach(hotel -> histogram.merge(hotel.getAddress().getCounty(), 1L, Long::sum));
                break;
            case "amenities":
                hotels.forEach(hotel -> hotel.getAmenities().forEach(a -> histogram.merge(a, 1L, Long::sum)));
                break;
            default:
                throw new IllegalArgumentException("Invalid histogram parameter: " + param);
        }
        return histogram;
    }
}

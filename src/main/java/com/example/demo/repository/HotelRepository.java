package com.example.demo.repository;

import com.example.demo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByNameContainingIgnoreCase(String name);
    List<Hotel> findByBrandContainingIgnoreCase(String brand);
    List<Hotel> findByAddress_CityContainingIgnoreCase(String city);
    List<Hotel> findByAddress_CountyContainingIgnoreCase(String county);
    List<Hotel> findByAmenitiesContainingIgnoreCase(String amenity);
}

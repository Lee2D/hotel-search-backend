package com.topflight.assessment.controller;

import com.topflight.assessment.model.Hotel;
import com.topflight.assessment.model.Photos;
import com.topflight.assessment.model.SearchRequest;
import com.topflight.assessment.repositories.PhotoRepository;
import com.topflight.assessment.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HotelController is a REST controller that handles HTTP requests
 * related to searching and listing hotels and their details.
 */
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private PhotoRepository photoRepository;

    /**
     * Returns a list of city names where hotels are available.
     *
     * @return a list of city names
     */
    @GetMapping("/cities")
    public List<String> getCities() {
        return hotelService.findAllCities();
    }

    /**
     * Searches for hotels based on the provided search request
     * and returns a list of hotels that match the search criteria.
     *
     * @param searchRequest the search request containing search criteria
     * @return a ResponseEntity containing a list of matching hotels
     */
    @PostMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(@RequestBody SearchRequest searchRequest) {
        List<Hotel> hotels = hotelService.searchHotels(searchRequest);
        for (Hotel hotel : hotels) {
            List<Photos> photos = photoRepository.findByHotelId(hotel.getId());
            hotel.setPhotos(photos);
        }
        return ResponseEntity.ok(hotels);
    }
}

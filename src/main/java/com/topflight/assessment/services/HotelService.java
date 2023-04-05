package com.topflight.assessment.services;

import com.topflight.assessment.model.Hotel;
import com.topflight.assessment.model.RoomCombination;
import com.topflight.assessment.model.RoomType;
import com.topflight.assessment.model.SearchRequest;
import com.topflight.assessment.repositories.HotelRepository;
import com.topflight.assessment.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * HotelService is a service class that provides methods for searching hotels,
 * retrieving hotel information, and finding valid room combinations based on
 * search criteria.
 */
@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    /**
     * Retrieves a list of distinct city names where hotels are available.
     *
     * @return a list of city names
     */
    public List<String> findAllCities() {
        return hotelRepository.findDistinctCities();
    }

    /**
     * Searches for hotels based on the provided search request and returns
     * a list of hotels that match the search criteria along with valid room
     * combinations for each hotel.
     *
     * @param searchRequest the search request containing search criteria
     * @return a list of matching hotels with valid room combinations
     */
    public List<Hotel> searchHotels(SearchRequest searchRequest) {
        List<Hotel> hotels = hotelRepository.findByCityAndPriceRange(searchRequest.getCity(), searchRequest.getMinPrice(), searchRequest.getMaxPrice());

        return hotels.stream()
                .peek(hotel -> {
                    List<RoomType> roomTypes = roomTypeRepository.findByHotelId(hotel.getId());
                    List<RoomCombination> combinations = findValidRoomCombinations(roomTypes, searchRequest.getNumGuests(), searchRequest.getMinPrice(), searchRequest.getMaxPrice());
                    hotel.setCombinations(combinations);
                })
                .filter(hotel -> !hotel.getCombinations().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Finds valid room combinations for a hotel based on the provided
     * room types, number of guests, and price range.
     *
     * @param roomTypes a list of available room types for the hotel
     * @param numGuests the number of guests to be accommodated
     * @param minPrice the minimum price for the search criteria
     * @param maxPrice the maximum price for the search criteria
     * @return a list of valid room combinations
     */
    private List<RoomCombination> findValidRoomCombinations(List<RoomType> roomTypes, int numGuests, double minPrice, double maxPrice) {
        List<RoomCombination> validCombinations = new ArrayList<>();

        for (int i = 0; i <= roomTypes.get(0).getMaxPeople(); i++) {
            for (int j = 0; j <= roomTypes.get(1).getMaxPeople(); j++) {
                for (int k = 0; k <= roomTypes.get(2).getMaxPeople(); k++) {
                    int totalPeople = i * roomTypes.get(0).getMaxPeople() + j * roomTypes.get(1).getMaxPeople() + k * roomTypes.get(2).getMaxPeople();
                    if (totalPeople == numGuests) {
                        double totalPrice = i * roomTypes.get(0).getPricePerPerson() + j * roomTypes.get(1).getPricePerPerson() + k * roomTypes.get(2).getPricePerPerson();
                        if (totalPrice >= minPrice && totalPrice <= maxPrice) {
                            Map<String, Integer> roomTypeCounts = new HashMap<>();
                            if (i > 0) roomTypeCounts.put(roomTypes.get(0).getTypeName(), i);
                            if (j > 0) roomTypeCounts.put(roomTypes.get(1).getTypeName(), j);
                            if (k > 0) roomTypeCounts.put(roomTypes.get(2).getTypeName(), k);
                            validCombinations.add(new RoomCombination(roomTypeCounts, totalPrice));
                        }
                    }
                }
            }
        }

        // Sort the valid combinations in ascending price order
        validCombinations.sort(Comparator.comparing(RoomCombination::getTotalPrice));

        return validCombinations;
    }

}

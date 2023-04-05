package com.topflight.assessment.repositories;

import com.topflight.assessment.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * HotelRepository interface extends JpaRepository to provide CRUD operations
 * for the Hotel entity. This interface includes custom queries to find
 * distinct cities and hotels based on the city and price range.
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    /**
     * Finds distinct city names from the Hotel table.
     *
     * @return A list of distinct city names.
     */
    @Query("SELECT DISTINCT h.city FROM Hotel h")
    List<String> findDistinctCities();

    /**
     * Finds hotels based on the city and price range.
     *
     * @param city     The city to search hotels in.
     * @param minPrice The minimum price per person.
     * @param maxPrice The maximum price per person.
     * @return A list of hotels that match the search criteria.
     */
    @Query(value = "SELECT h.* FROM hotel h " +
            "JOIN room_type rt ON h.id = rt.hotel_id " +
            "WHERE h.city = ?1 AND " +
            "rt.price_per_person >= ?2 AND rt.price_per_person <= ?3 " +
            "GROUP BY h.id", nativeQuery = true)
    List<Hotel> findByCityAndPriceRange(String city, Double minPrice, Double maxPrice);
}

package com.topflight.assessment.repositories;

import com.topflight.assessment.model.Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PhotoRepository interface extends JpaRepository to provide CRUD operations
 * for the Photos entity. This interface includes a custom query to find
 * photos based on the hotel ID.
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photos, Long> {

    /**
     * Finds photos associated with a specific hotel ID.
     *
     * @param hotelId The hotel ID to search photos for.
     * @return A list of photos that belong to the specified hotel.
     */
    List<Photos> findByHotelId(Integer hotelId);
}

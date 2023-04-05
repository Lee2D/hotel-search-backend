package com.topflight.assessment.repositories;

import com.topflight.assessment.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoomTypeRepository interface extends JpaRepository to provide CRUD operations
 * for the RoomType entity. This interface includes a custom query to find
 * room types based on the hotel ID.
 */
@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    /**
     * Finds room types associated with a specific hotel ID.
     *
     * @param hotelId The hotel ID to search room types for.
     * @return A list of room types that belong to the specified hotel.
     */
    List<RoomType> findByHotelId(Integer hotelId);
}

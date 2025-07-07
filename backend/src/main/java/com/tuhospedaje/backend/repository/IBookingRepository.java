package com.tuhospedaje.backend.repository;

import com.tuhospedaje.backend.entity.Booking;
import com.tuhospedaje.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
                SELECT COUNT(b) > 0
                FROM Booking b
                WHERE b.room = :room
                AND b.status = 'CONFIRMED'
                AND (:checkIn < b.checkOutDate AND :checkOut > b.checkInDate)
            """)
    boolean existsByRoomAndDates(
            @Param("room") Room room,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );
}

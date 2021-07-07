package com.example.demo.services

import com.example.demo.entities.booking.Booking
import com.example.demo.repositories.BookingRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class BookingService(val db: BookingRepository) {

    fun findAllBookings(): List<Booking> = db.findAll()

    fun findBookingById(id: Long): Booking =
        // similar behavior as when invoking db.deleteById(...)
        db.findById(id).orElseThrow { EmptyResultDataAccessException(1) }

    fun createBooking(booking: Booking): Booking = db.save(booking)

    fun updateBooking(booking: Booking): Booking = db.save(booking)

    fun deleteBookingById(id: Long) = db.deleteById(id)
}

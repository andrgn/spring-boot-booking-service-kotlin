package com.example.demo.repositories

import com.example.demo.entities.booking.Booking
import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository: JpaRepository<Booking, Long>

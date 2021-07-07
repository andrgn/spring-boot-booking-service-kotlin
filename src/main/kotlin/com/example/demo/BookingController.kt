package com.example.demo

import com.example.demo.entities.booking.Booking
import com.example.demo.exceptions.CheckoutIsBeforeCheckinException
import com.example.demo.services.BookingService
import com.example.demo.utils.isCheckoutBeforeCheckin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Validated
class BookingController(private val bookingService: BookingService) {

    @GetMapping("/bookings")
    fun getAllBookings(): ResponseEntity<List<Booking>> =
        ResponseEntity.ok(bookingService.findAllBookings())

    @PostMapping("/bookings")
    fun createBooking(@Valid @RequestBody booking: Booking): ResponseEntity<Booking> {
        if (isCheckoutBeforeCheckin(booking)) throw CheckoutIsBeforeCheckinException()
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking))
    }

    @GetMapping("/bookings/{id}")
    fun getBooking(@PathVariable("id") id: Long): ResponseEntity<Booking> =
        ResponseEntity.ok(bookingService.findBookingById(id))

    @PutMapping("/bookings")
    fun updateBooking(@Valid @RequestBody booking: Booking): ResponseEntity<Booking> {
        if (isCheckoutBeforeCheckin(booking)) throw CheckoutIsBeforeCheckinException()
        return ResponseEntity.ok(bookingService.updateBooking(booking))
    }

    @DeleteMapping("/bookings/{id}")
    fun deleteBooking(@PathVariable id: Long) : ResponseEntity<Booking> {
        bookingService.deleteBookingById(id)
        return ResponseEntity.noContent().build()
    }
}

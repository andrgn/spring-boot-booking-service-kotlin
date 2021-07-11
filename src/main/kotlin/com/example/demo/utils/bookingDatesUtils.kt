package com.example.demo.utils

import com.example.demo.entities.booking.Booking

fun isCheckoutBeforeCheckin(booking: Booking): Boolean  {
    val (checkin, checkout) = booking.bookingDates

    return checkout.isBefore(checkin)
}


package com.example.demo.entities.booking.subentities

import java.time.LocalDate
import javax.persistence.Embeddable
import javax.validation.constraints.FutureOrPresent
import javax.validation.constraints.NotNull

@Embeddable
class BookingDates(

    @get:NotNull
    @get:FutureOrPresent
    var checkin: LocalDate,

    @get:NotNull
    @get:FutureOrPresent
    var checkout: LocalDate
) {
    operator fun component1() = checkin
    operator fun component2() = checkout
}

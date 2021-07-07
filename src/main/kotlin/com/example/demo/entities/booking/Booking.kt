package com.example.demo.entities.booking

import com.example.demo.entities.booking.subentities.BookingDates
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.*

@Entity
class Booking(

    @Id
    @GeneratedValue
    val id: Long?,

    @get:NotBlank(message = "firstname must not be empty")
    @get:Size(
        min=5,
        max=30,
        message = "firstname must be between {min} and {max} characters inclusive")
    var firstname: String,

    @get:NotBlank(message = "lastname must not be empty")
    @get:Size(
        min=5,
        max=30,
        message = "lastname must be between {min} and {max} characters inclusive")
    var lastname: String,

    @get:NotNull
    @get:Min(0)
    @get:JsonProperty("totalprice")
    var totalPrice: Int,

    @get:NotNull
    @get:JsonProperty("depositpaid")
    var depositPaid: Boolean,

    @get:NotNull
    @get:Size(
        min=0,
        max=150,
        message = "additionalneeds must be no more than {max} characters")
    @get:JsonProperty("additionalneeds")
    var additionalNeeds: String,

    @get:Embedded
    @get:Valid
    @get:NotNull
    @get:JsonProperty("bookingdates")
    var bookingDates: BookingDates
)

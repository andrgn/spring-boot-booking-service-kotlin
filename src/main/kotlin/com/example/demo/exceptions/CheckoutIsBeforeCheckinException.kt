package com.example.demo.exceptions

import java.lang.RuntimeException

class CheckoutIsBeforeCheckinException : RuntimeException("checkout must be on the day of checkin or later")

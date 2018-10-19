package com.helpinghands.retrofit.requests

data class CreateEventRequest (

        val name: String,
        val mobile: String,
        val email: String,
        val address: String,
        val date: String,
        val time: String,
        val num_people: Int,
        val total_volunteers: Int,
        val is_pickup_available: Boolean,
        val category: String

)

package com.helpinghands.retrofit

import com.helpinghands.Model.Event
import com.helpinghands.helper.Constants

data class EmailBody(
        val email: String
)

data class listEvents(val events: ArrayList<eventsResponse>)

data class OngoinglistEvents(val ongoing_events: ArrayList<eventsResponse>)
data class PreviouslistEvents(val previous_events: ArrayList<eventsResponse>)
data class AllOngoingEvents(val all_ongoing_events: ArrayList<eventsResponse>)
data class eventsResponse(
        val object_id: Int,
        val volunteers_left: Int,
        val email: String,
        val content_type_id: Int,
        val contact_number: String,
        val address: String,
        val is_pickup_available: Boolean,
        val total_volunteers: String,
        val volunteers_volunteered: Int,
        val category: String,
        val time: String,
        val name: String,
        val id: Int,
        val is_completed: Boolean,
        val date: String,
        val number_of_people: Int,
        var eventType: String

)


data class listOfEvents(
        val events: ArrayList<Event>
)
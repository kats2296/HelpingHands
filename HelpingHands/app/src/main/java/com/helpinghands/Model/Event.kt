package com.helpinghands.Model

data class Event(
        val eventName: String,
        val eventLocation: String,
        val orgName: String,
        val dateTime: String,
        val volunteers: Int,
        val category: String)
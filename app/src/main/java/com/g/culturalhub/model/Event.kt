package com.g.culturalhub.model

// Un "model": doar datele unui eveniment, fără logică.
// data class ne dă automat equals(), toString(), copy() etc.
data class Event(
    val id: Int,
    val title: String,
    val category: String,
    val venue: String,
    val city: String,
    val date: String,
    val priceFrom: Int,
    val imageUrl: String
)
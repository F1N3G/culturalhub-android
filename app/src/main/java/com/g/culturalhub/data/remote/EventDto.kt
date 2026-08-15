package com.g.culturalhub.data.remote

import com.google.gson.annotations.SerializedName

// Wrapper-ul paginat: GET /api/events -> { "data": [...], "meta": {...} }
data class EventsResponseDto(
    @SerializedName("data") val data: List<EventDto>
)

// Wrapper pentru detaliu: GET /api/events/{id} -> { "data": { ...eveniment... } }
data class EventDetailResponseDto(
    @SerializedName("data") val data: EventDto
)

// Un eveniment, exact cum vine în JSON.
data class EventDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("is_free") val isFree: Boolean,
    @SerializedName("price") val price: String?,
    @SerializedName("price_max") val priceMax: String?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("venue") val venue: VenueDto?,
    @SerializedName("category") val category: CategoryDto?
)

data class VenueDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class CategoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
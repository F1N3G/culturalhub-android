package com.g.culturalhub.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface CulturalHubApi {

    @GET("events")
    suspend fun getEvents(): EventsResponseDto

    @GET("events/{id}")
    suspend fun getEvent(@Path("id") id: Int): EventDetailResponseDto
}
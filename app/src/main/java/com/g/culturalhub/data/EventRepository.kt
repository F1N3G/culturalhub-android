package com.g.culturalhub.data

import com.g.culturalhub.data.remote.ApiClient
import com.g.culturalhub.data.remote.CulturalHubApi
import com.g.culturalhub.data.remote.toEvent
import com.g.culturalhub.model.Event

class EventRepository(
    private val api: CulturalHubApi = ApiClient.api
) {
    // lista de evenimente
    suspend fun getEvents(): List<Event> =
        api.getEvents().data.map { it.toEvent() }

    // un singur eveniment, după id
    suspend fun getEvent(id: Int): Event =
        api.getEvent(id).data.toEvent()
}
package com.valentinerutto.nightlife.data.network

import retrofit2.http.GET

interface NightlifeApiService {

    @GET("events")
    suspend fun getEvents(): List<EventDTO>

}
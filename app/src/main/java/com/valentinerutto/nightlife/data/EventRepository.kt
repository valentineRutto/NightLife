package com.valentinerutto.nightlife.data

import com.valentinerutto.nightlife.MyApplication
import com.valentinerutto.nightlife.data.NotificationWorker.Companion.schedule
import com.valentinerutto.nightlife.data.local.EventDao
import com.valentinerutto.nightlife.data.local.EventEntity
import com.valentinerutto.nightlife.data.local.toDomain
import com.valentinerutto.nightlife.data.network.NightlifeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val api: NightlifeApiService, private val dao: EventDao
){
     fun getEvents():Flow<List<Event>> =
        dao.getEvents().map { it.map { e -> e.toDomain() } }
fun getEventsByID(id:String):Event =
    dao.getEventByID(id).toDomain()
    suspend fun fetchEvents() {
        try {

        val remote = api.getEvents()

        val entities = remote.map {

            EventEntity(
                it.id,
                it.title,
                it.description,
                it.imageUrl,
                it.location,
                it.dateTime,
                it.price,
                it.isSoldOut,
                it.category
            )

        }

        dao.insertAll(entities)


    } catch (e: Exception) {
        // fallback silently (offline-first)
    }
    }

    suspend fun bookEvent(value: BookingUiState) {
        schedule(MyApplication.INSTANCE, value.bookingRef)

    }

}
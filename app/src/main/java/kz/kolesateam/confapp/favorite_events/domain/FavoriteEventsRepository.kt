package kz.kolesateam.confapp.favorite_events.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ResponseData

interface FavoriteEventsRepository {
    fun saveFavoriteEvent(
            eventApiData: EventApiData
    )

    fun removeFavoriteEvent(
            eventId: Int?
    )

    fun getAllFavoriteEvents():List<EventApiData>

    fun isFavorite(id: Int?): Boolean

}
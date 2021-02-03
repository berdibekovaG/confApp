package kz.kolesateam.confapp.event_details.data

import kz.kolesateam.confapp.events.data.models.EventApiData

interface EventIdDataSource {
//    EventApiData
    fun getEventId(): Int

    fun setEventId(
            eventId: EventApiData
    )
}
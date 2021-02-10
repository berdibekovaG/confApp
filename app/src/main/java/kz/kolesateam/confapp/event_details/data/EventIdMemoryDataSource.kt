package kz.kolesateam.confapp.event_details.data

import kz.kolesateam.confapp.events.data.models.EventApiData

class EventIdMemoryDataSource : EventIdDataSource {

    private lateinit var eventId: EventApiData

    override fun getEventId(): Int = eventId as Int

    override fun setEventId(eventId: EventApiData) {
        this.eventId = eventId
    }
}
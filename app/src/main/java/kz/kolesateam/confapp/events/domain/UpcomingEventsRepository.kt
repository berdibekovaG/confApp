package kz.kolesateam.confapp.events.domain

import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem

interface UpcomingEventsRepository {
    fun getUpcomingEvents(): ResponseData<List<UpcomingEventListItem>, Exception>
}
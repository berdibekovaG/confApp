package kz.kolesateam.confapp.allevents.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem

interface AllEventsActivityRepository {
    fun getAllEvents(branchId: Int): ResponseData<List<UpcomingEventListItem>, Exception>
}
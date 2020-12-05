package kz.kolesateam.confapp.allevents.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ResponseData

interface AllEventsActivityRepository {
    fun getAllEvents(): ResponseData<List<EventApiData>, Exception>
}
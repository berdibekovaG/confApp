package kz.kolesateam.confapp.event_details.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.kolesateam.confapp.event_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper

class EventDetailsViewModel (
    private val eventDetailsRepository: EventDetailsRepository,
    private val favoritesRepository: FavoriteEventsRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper
): ViewModel(){
    val eventDetailsLiveData: MutableLiveData<ResponseData<EventApiData, String>> = MutableLiveData()
    private val eventIdLiveData: MutableLiveData<Int> = MutableLiveData()

    fun onStart(
            eventId: Int
    ) {
        eventIdLiveData.value = eventId
        getEventDetails()
    }

    fun onFavoriteClick(eventApiData: EventApiData){
        when(eventApiData.isFavorite){
            true -> {
                favoritesRepository.saveFavoriteEvent(eventApiData)
                scheduleEvent(eventApiData)
            }
            else -> {
                favoritesRepository.removeFavoriteEvent(eventId = eventApiData.id)
                cancelNotificationEvent(eventApiData)
            }
        }
    }

    private fun scheduleEvent(eventApiData: EventApiData) {
        notificationAlarmHelper.createNotificationAlarm(eventApiData)
    }

    private fun cancelNotificationEvent(eventApiData: EventApiData) {
        notificationAlarmHelper.cancelNotificationAlarm(eventApiData)
    }

    private fun getEventDetails() {
        viewModelScope.launch {
            eventDetailsRepository.getEventDetails(
                result = {
                    eventDetailsLiveData.value = ResponseData.Success(it)
                },
                fail = {
                    eventDetailsLiveData.value = ResponseData.Error(it.toString())
                },
                eventId = eventIdLiveData.value ?: 0
            )
        }
    }
}
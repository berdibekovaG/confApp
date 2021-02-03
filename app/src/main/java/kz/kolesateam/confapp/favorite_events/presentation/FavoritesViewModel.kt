package kz.kolesateam.confapp.favorite_events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper

class FavoritesViewModel(
    private val favoritesRepository: FavoriteEventsRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper
): ViewModel() {

    private val favoriteEventsLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()
    fun getFavoriteEventsLiveData(): LiveData<List<EventApiData>> = favoriteEventsLiveData

    fun onStart(){
        favoriteEventsLiveData.value = favoritesRepository.getAllFavoriteEvents()
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
}
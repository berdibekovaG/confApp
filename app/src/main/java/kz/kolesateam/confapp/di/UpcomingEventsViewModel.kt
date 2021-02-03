package kz.kolesateam.confapp.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.dataSource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper

private const val DEFAULT_USER_NAME = "Гость"

class UpcomingEventsViewModel(
        private val upcomingEventsRepository: UpcomingEventsRepository,
        private val favoriteEventsRepository: FavoriteEventsRepository,
        private val userNameDataSource: UserNameDataSource,
        private val notificationAlarmHelper: NotificationAlarmHelper
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val upcomingEventLiveData: MutableLiveData<List<UpcomingEventListItem>> =
            MutableLiveData()
    private val errorEventLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData
    fun geUpcomingEventLiveData(): LiveData<List<UpcomingEventListItem>> = upcomingEventLiveData
    fun getErrorEventLiveData(): LiveData<Exception> = errorEventLiveData

    fun onStart() {
        getUpcomingEvents()
    }

    fun onFavoriteClick(eventData: EventApiData) {
        when (eventData.isFavorite) {
            true -> {
                favoriteEventsRepository.saveFavoriteEvent(eventData)
                scheduleEvent(eventData)

            }
            else -> favoriteEventsRepository.removeFavoriteEvent(eventId = eventData.id)
        }
    }

    private fun scheduleEvent(eventData: EventApiData) {
        notificationAlarmHelper.createNotificationAlarm(eventData)
    }

    private fun getUpcomingEvents() {
        progressLiveData.value = ProgressState.Loading
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val response: ResponseData<List<UpcomingEventListItem>, Exception> =
                    withContext(Dispatchers.IO) {
                        upcomingEventsRepository.getUpcomingEvents()
                    }

            when (response) {
                is ResponseData.Success -> {
                    upcomingEventLiveData.value = prepareUpcomingEventsList(response.result)
                }
                is ResponseData.Error -> {
                    errorEventLiveData.value = response.error
                }
            }
            progressLiveData.value = ProgressState.Done
        }
    }

    private fun prepareUpcomingEventsList(
            upcomingEvents: List<UpcomingEventListItem>
    ): List<UpcomingEventListItem> {

        val upcomingEventListItemList: MutableList<UpcomingEventListItem> =
                mutableListOf()
        val headerListItem: UpcomingEventListItem = UpcomingEventListItem(
                type = 1,
                data = getUserName()
        )
        //сформировали новый список где первый - header

        upcomingEvents.forEach {
            val branchApiData: BranchApiData = it.data as? BranchApiData ?: return@forEach

            branchApiData.events.forEach { eventApiData ->
                eventApiData.isFavorite = favoriteEventsRepository.isFavorite(eventApiData.id)
            }
        }
        upcomingEventListItemList.addAll(listOf(headerListItem) + upcomingEvents)

        return upcomingEventListItemList
    }

    private fun getUserName(): String {
        return userNameDataSource.getUserName() ?: DEFAULT_USER_NAME
    }
}


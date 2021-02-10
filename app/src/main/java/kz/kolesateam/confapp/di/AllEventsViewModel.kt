package kz.kolesateam.confapp.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.allevents.data.BranchIdDataSource
import kz.kolesateam.confapp.allevents.domain.AllEventsActivityRepository
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import java.lang.Exception

class AllEventsViewModel(
        private val allEventsRepository: AllEventsActivityRepository,
        private val branchIdDataSource: BranchIdDataSource,
        private val favoritesRepository: FavoriteEventsRepository,
        private val notificationAlarmHelper: NotificationAlarmHelper
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val allUpcomingEventLiveData: MutableLiveData<List<UpcomingEventListItem>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData
    fun getAllUpcomingEventLiveData(): LiveData<List<UpcomingEventListItem>> = allUpcomingEventLiveData
    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStart() {
        getAllEvents(branchIdDataSource.getBranchId())
    }

    private fun getAllEvents(branchId: BranchApiData) {
        progressLiveData.value = ProgressState.Loading
        GlobalScope.launch(Dispatchers.Main) {
            val response: ResponseData<List<UpcomingEventListItem>, Exception> = withContext(
                    Dispatchers.IO) {
                allEventsRepository.getAllEvents(branchId.id as Int)
            }
            when (response) {
                is ResponseData.Success -> allUpcomingEventLiveData.value = prepareAllEventsList(response.result)
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressState.Done
        }
    }

    private fun scheduleEvent(eventApiData: EventApiData) {
        notificationAlarmHelper.createNotificationAlarm(eventApiData)
    }

    private fun cancelNotificationEvent(eventApiData: EventApiData) {
        notificationAlarmHelper.cancelNotificationAlarm(eventApiData)
    }

    fun onFavoriteClick(eventApiData: EventApiData) {
        when (eventApiData.isFavorite) {
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

    private fun prepareAllEventsList(
            allEvents: List<UpcomingEventListItem>
    ): List<UpcomingEventListItem> {
        val branch = branchIdDataSource.getBranchId()
        val allEventListWithBranchName: MutableList<UpcomingEventListItem> = mutableListOf()
        val branchNameData = UpcomingEventListItem(
                type = 1,
                data = branch
        )
        allEventListWithBranchName.add(branchNameData)
        allEvents.forEach {
            val branchApiData: BranchApiData = it.data as? BranchApiData ?: return@forEach

            branchApiData.events.forEach { eventApiData ->
                eventApiData.isFavorite = favoritesRepository.isFavorite(eventApiData.id)
            }
        }
        allEventListWithBranchName.addAll(allEvents)
        return allEventListWithBranchName
    }
}

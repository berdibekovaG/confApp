package kz.kolesateam.confapp.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.allevents.domain.AllEventsActivityRepository
import kz.kolesateam.confapp.events.data.dataSource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ProgressState
import kz.kolesateam.confapp.events.data.models.ResponseData

class AllEventsViewModel(
    private val allEventsActivityRepository: AllEventsActivityRepository,
    private val userNameDataSource: UserNameDataSource
):ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val allUpcomingEventLiveData: MutableLiveData<List<EventApiData>> =
        MutableLiveData()

    fun getProgressLiveData() : LiveData<ProgressState> = progressLiveData
    fun getAllUpcomingEventLiveData() : LiveData<List<EventApiData>> = allUpcomingEventLiveData

    fun onStart() {
        getAllEvents()
    }

    private fun getAllEvents() {
        progressLiveData.value = ProgressState.Loading
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val response =
                withContext(Dispatchers.IO) {
                    allEventsActivityRepository.getAllEvents()
                }

            when (response) {
                is ResponseData.Success -> allUpcomingEventLiveData.value = response.result
                is ResponseData.Error -> println(response.error)
            }
            progressLiveData.value = ProgressState.Done
        }
    }

    }

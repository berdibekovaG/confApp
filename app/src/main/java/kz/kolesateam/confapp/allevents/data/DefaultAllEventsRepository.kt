package kz.kolesateam.confapp.allevents.data

import kz.kolesateam.confapp.allevents.domain.AllEventsActivityRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ResponseData


class DefaultAllEventsRepository(
    private val upcomingAllEventsDataSource: AllEventsDataSource
): AllEventsActivityRepository{

    override fun getAllEvents(): ResponseData<List<EventApiData>, Exception> {
        return try {
            val responseData = upcomingAllEventsDataSource.getAllUpcomingEvents().execute()
            if (responseData.isSuccessful){
                val eventApiDataList:List<EventApiData> = responseData.body()!!
                ResponseData.Success(eventApiDataList)
            }else {
                val exception = Exception("Ошибка в получении данных")
                ResponseData.Error(exception)
            }
        } catch (e: Exception) {
            ResponseData.Error(e)
        }
    }
}




package kz.kolesateam.confapp.events.data.models

import kz.kolesateam.confapp.events.data.dataSource.UpcomingEventDataSource
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository

private const val ERROR_MESSAGE = "Error occurred"

class DefaultUpcomingEventsRepository(
        private val upcomingEventsDataSource: UpcomingEventDataSource
) : UpcomingEventsRepository {

    override fun getUpcomingEvents(): ResponseData<List<UpcomingEventListItem>, Exception> {

        return try {
            val response = upcomingEventsDataSource.getUpcomingEvents().execute()

            if (response.isSuccessful) {

                var upcomingEventList: List<UpcomingEventListItem> = mutableListOf()
                upcomingEventList = response.body()!!.map { branchApiData ->
                    UpcomingEventListItem(
                            type = 2,
                            data = branchApiData
                    )
                }

                ResponseData.Success(upcomingEventList)
            } else {
                ResponseData.Error(Exception(ERROR_MESSAGE))
            }
        } catch (e: Exception) {
            ResponseData.Error(Exception(ERROR_MESSAGE))
        }
    }
}
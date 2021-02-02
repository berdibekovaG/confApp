package kz.kolesateam.confapp.allevents.data

import kz.kolesateam.confapp.allevents.domain.AllEventsActivityRepository
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
const val ERROR_MESSAGE = "Error occurred"

class DefaultAllEventsRepository(
    private val upcomingAllEventsDataSource: AllEventsDataSource
) : AllEventsActivityRepository {

    override fun getAllEvents(branchId: Int): ResponseData<List<UpcomingEventListItem>, Exception> {
        return try {
            val responseData = upcomingAllEventsDataSource.getAllUpcomingEvents(branchId).execute()
            if (responseData.isSuccessful) {
                val branchListItemList: List<UpcomingEventListItem> =
                    responseData.body()!!.map{ branchApiData->
                        UpcomingEventListItem(
                            type = 2,
                            data = branchApiData
                        )
                    }
                (ResponseData.Success(branchListItemList))
            } else {
                ResponseData.Error(Exception(ERROR_MESSAGE))
            }
        } catch (e: Exception) {
           return ResponseData.Error(e)
        }
    }
}




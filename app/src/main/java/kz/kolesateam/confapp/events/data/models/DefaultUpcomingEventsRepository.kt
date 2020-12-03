package kz.kolesateam.confapp.events.data.models

import android.widget.ProgressBar
import androidx.core.view.isVisible
import kz.kolesateam.confapp.events.data.dataSource.UpcomingEventDataSource
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import okhttp3.Response

private const val ERROR_MESSAGE = "Error occurred"

//repository for load api data
class DefaultUpcomingEventsRepository(
    private val upcomingEventsDataSource: UpcomingEventDataSource
): UpcomingEventsRepository {

    override fun getUpcomingEvents(): ResponseData<List<UpcomingEventListItem>, Exception> {

        return try{
            val response = upcomingEventsDataSource.getUpcomingEvents().execute()

            if(response.isSuccessful){

                var upcomingEventList: List<UpcomingEventListItem> = mutableListOf()
                upcomingEventList = response.body()!!.map { branchApiData ->
                    UpcomingEventListItem(
                        type = 2,
                        data = branchApiData
                    )
                }

                ResponseData.Success(upcomingEventList)
            }else{
                ResponseData.Error(Exception(ERROR_MESSAGE))
            }
        }catch (e: Exception){
            ResponseData.Error(Exception(ERROR_MESSAGE))
        }
    }
}
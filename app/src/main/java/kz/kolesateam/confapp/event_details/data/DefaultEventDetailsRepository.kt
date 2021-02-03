package kz.kolesateam.confapp.event_details.data


import kz.kolesateam.confapp.event_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import retrofit2.awaitResponse
import java.lang.Exception

class DefaultEventDetailsRepository(
    private val eventDetailsDataSource: EventDetailsDataSource
): EventDetailsRepository {
    override suspend fun getEventDetails(
        result: (EventApiData) -> Unit,
        fail: (String?) -> Unit,
        eventId: Int
    ) {
        try {
            val response = eventDetailsDataSource.getEventDetails(eventId).awaitResponse()
            if(response.isSuccessful){
                result(response.body()!!)
            } else {
                fail(response.message())
            }
        } catch (e: Exception) {
            fail(e.localizedMessage)
        }
    }
    }
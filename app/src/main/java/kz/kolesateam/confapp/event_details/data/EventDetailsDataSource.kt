package kz.kolesateam.confapp.event_details.data

import kz.kolesateam.confapp.events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface
EventDetailsDataSource {
    @GET("/events/{event_id}")
    //SpeakerApiData -> EventApiData
    //Call<List<EventApiData>> -> Call<EventApiData>
    fun getEventDetails(@Path("event_id") eventId: Int): Call<EventApiData>
}
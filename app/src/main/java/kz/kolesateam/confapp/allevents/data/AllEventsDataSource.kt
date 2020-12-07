package kz.kolesateam.confapp.allevents.data

import kz.kolesateam.confapp.events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AllEventsDataSource {
    @GET("/branch_events/{branchId}")
    fun getAllUpcomingEvents(
        @Path("branchId") branchId: Int = 0
    ): Call<List<EventApiData>>
}
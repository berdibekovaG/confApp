package kz.kolesateam.confapp.events.data.dataSource

import kz.kolesateam.confapp.events.data.models.BranchApiData
import retrofit2.Call
import retrofit2.http.GET

interface UpcomingEventDataSource {
    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>
}
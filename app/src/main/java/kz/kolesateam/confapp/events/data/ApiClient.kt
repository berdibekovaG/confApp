package kz.kolesateam.confapp.events.data

import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.events.data.models.BranchApiData
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {

    @GET("/upcoming_events")
    fun getUpcomingSynsEvents(): Call<JsonNode>

    @GET("/upcoming_events")
    fun getUpcomingAsynsEvents(): Call<List<BranchApiData>>
}
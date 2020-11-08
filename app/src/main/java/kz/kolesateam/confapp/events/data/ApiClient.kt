package kz.kolesateam.confapp.events.data

import android.util.Log
import android.widget.TextView
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.events.presentation.apiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<JsonNode>

}
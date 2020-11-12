package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.Exception

private const val BASE_URL = " http://37.143.8.68:2020"

val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var responseTextView: TextView
    private lateinit var loadDataButtonSync: Button
    private lateinit var loadDataButtonAsync: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.progressbar)
        responseTextView = findViewById(R.id.activity_upcoming_events_response_textview)
        loadDataButtonSync = findViewById(R.id.activity_upcoming_events_sync_btn)
        loadDataButtonAsync = findViewById(R.id.activity_upcoming_events_async_btn)

        loadDataButtonSync.setOnClickListener() {
            progressBar.show()
            loadApiDataSync()
        }

        loadDataButtonAsync.setOnClickListener() {
            progressBar.show()
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync() {
        Thread {
            try {
                val response: Response<JsonNode> = apiClient.getUpcomingSynsEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        responseTextView.setTextColor(ContextCompat.getColor(this, R.color.activity_upcome_events_sync_text))
                        responseTextView.text = body.toString()
                        val responseJsonArray = JSONArray(body.toString())
                        val branchApiDataList = parseBranchesJsonArray(responseJsonArray)
                        progressBar.gone()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    responseTextView.text = e.localizedMessage
                    responseTextView.setTextColor(ContextCompat.getColor(this, R.color.activity_upcome_events_fail_text))
                    progressBar.gone()
                }
            }
        }.start()
    }

    private fun parseBranchesJsonArray(
            responseJsonArray: JSONArray
    ): List<BranchApiData> {
        val branchList = mutableListOf<BranchApiData>()
        for (index in 0 until responseJsonArray.length()) {
            val branchJSonObject = (responseJsonArray[index] as? JSONObject) ?: continue
            val apiBranchData = parseBranchJsonObject(branchJSonObject)
            branchList.add(apiBranchData)
        }
        return branchList
    }

    private fun parseBranchJsonObject(
            branchJsonObject: JSONObject
    ): BranchApiData {
        val id = branchJsonObject.getInt("id")
        val title = branchJsonObject.getString("title")
        val eventsJsonArray = branchJsonObject.getJSONArray("events")

        val apiEventsList = mutableListOf<EventApiData>()
        for (index in 0 until eventsJsonArray.length()) {
            val eventJsonObject = (eventsJsonArray[index] as? JSONObject) ?: continue

            val apiEventData = parseEventJsonObject(eventJsonObject)

            apiEventsList.add(apiEventData)
        }
        return BranchApiData(
                id = id,
                title = title,
                events = apiEventsList
        )
    }

    private fun parseEventJsonObject(
            eventJSONObject: JSONObject
    ): EventApiData {
        val id = eventJSONObject.getInt("id")
        val title = eventJSONObject.getString("title")
        val startTime = eventJSONObject.getString("startTime")
        val endTime = eventJSONObject.getString("endTime")
        val description = eventJSONObject.getString("description")
        val place = eventJSONObject.getString("place")
        val speakerJsonObject: JSONObject? = (eventJSONObject.get("speaker") as? JSONObject)
        var speakerData: SpeakerApiData? = null

        speakerJsonObject?.let {
            speakerData = parseSpeakerJsonObject(it)
        }
        return EventApiData(
                id = id,
                startTime = startTime,
                endTime = endTime,
                title = title,
                description = description,
                place = place,
                speaker = speakerData
        )
    }

    private fun parseSpeakerJsonObject(
            speakerJsonObject: JSONObject
    ): SpeakerApiData {
        val id = speakerJsonObject.getInt("id")
        val fullName = speakerJsonObject.getString("fullName")
        val job = speakerJsonObject.getString("job")
        val photoUrl = speakerJsonObject.getString("photoUrl")

        return SpeakerApiData(
                id = id,
                fullName = fullName,
                job = job,
                photoUrl = photoUrl
        )
    }

    private fun loadApiDataAsync() {
        apiClient.getUpcomingAsynsEvents().enqueue(object : Callback<List<BranchApiData>> {

            override fun onResponse(
                    call: Call<List<BranchApiData>>,
                    response: Response<List<BranchApiData>>) {
                if (response.isSuccessful) {
                    responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, R.color.activity_upcome_events_async_text))
                    val responseBody = response.body()!!

                    val apiBranchDataList = responseBody

                    progressBar.gone()
                }
            }

            override fun onFailure(
                    call: Call<List<BranchApiData>>,
                    t: Throwable
            ) {
                responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, R.color.activity_upcome_events_fail_text))
                responseTextView.text = t.localizedMessage
                progressBar.gone()
            }
        })
    }
}
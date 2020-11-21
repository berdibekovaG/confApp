package kz.kolesateam.confapp.events.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://37.143.8.68:2020")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var responceTextView: TextView
    private lateinit var loadDataButtonSync: Button
    private lateinit var loadDataButtonAsync: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()
    }

    private fun bindViews(){
        responceTextView = findViewById(R.id.upcoming_events_textView)
        loadDataButtonSync = findViewById(R.id.upcoming_events_syncLoad)
        loadDataButtonAsync = findViewById(R.id.upcoming_events_aSyncLoad)
        progressBar = findViewById(R.id.events_progress_bar)
        loadDataButtonSync.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            loadApiDataSync()
        }
        loadDataButtonAsync.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync(){
        apiClient.getUpcomingEventsSync().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseBody = response.body()!!
                val responseJsonString = responseBody.string()
                val responseJsonArray = JSONArray(responseJsonString)

                val apiBranchDataList = parseBranchesJsonArray(responseJsonArray)

                progressBar.visibility = View.GONE
                responceTextView.setTextColor(Color.parseColor("#FF000000"))
                responceTextView.text = apiBranchDataList.toString()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBar.visibility = View.GONE
                responceTextView.setTextColor(Color.parseColor("#F44336"))
                responceTextView.text = t.localizedMessage
            }

        })
    }

    private fun loadApiDataAsync(){
        apiClient.getUpcomingEventsAsync().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                val responseBody = response.body()!!

                val apiBranchDataList = responseBody

                progressBar.visibility = View.GONE
                responceTextView.setTextColor(Color.parseColor("#FF000000"))
                responceTextView.text = apiBranchDataList.toString()
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                progressBar.visibility = View.GONE
                responceTextView.setTextColor(Color.parseColor("#F44336"))
                responceTextView.text = t.localizedMessage
            }

        })
    }

    private fun parseBranchesJsonArray(
            responseJsonArray: JSONArray
    ): List<BranchApiData> {
        val branchList = mutableListOf<BranchApiData>()
        for (index in 0 until responseJsonArray.length()) {

            val branchJsonObject = (responseJsonArray[index] as? JSONObject) ?: continue

            val BranchApiData = parseBranchJsonObject(branchJsonObject)

            branchList.add(BranchApiData)
        }
        return branchList
    }

    private fun parseBranchJsonObject(
            branchJsonObject: JSONObject
                ): BranchApiData {
        val id = branchJsonObject.getInt("id")
        val title = branchJsonObject.getString("title")

        val eventsJsonArray = branchJsonObject.getJSONArray("events")

        val apiEventList = mutableListOf<EventApiData>()

        for (index in 0 until eventsJsonArray.length()) {
            val eventJsonObject = (eventsJsonArray[index] as? JSONObject) ?: continue
            val EventApiData = parseEventJsonObject(eventJsonObject)
            apiEventList.add(EventApiData)
        }

        return BranchApiData (
                id = id,
                title = title,
                events = apiEventList
        )
    }

    private fun parseEventJsonObject(
            eventJsonObject: JSONObject
    ): EventApiData {
        val id = eventJsonObject.getInt("id")
        val title = eventJsonObject.getString("title")

       val speakerJsonObject: JSONObject? = (eventJsonObject.get("speaker") as? JSONObject)
        var speakerData: SpeakerApiData? = null

        speakerJsonObject?.let {
            speakerData = parseSpeakerJsonObject(it)
        }
        return EventApiData(
                id = id,
                title = title,
                speaker = speakerData
        )
    }

    private fun parseSpeakerJsonObject(
            speakerJsonObject: JSONObject
    ): SpeakerApiData = SpeakerApiData(
            fullName = speakerJsonObject.getString("fullName")
    )

    /*private fun loadApiDataAsync(){
       apiClient.getUpcomingEvents().enqueue(object :Callback<JsonNode> {
           override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
               if (response.isSuccessful) {
                   progressBar.visibility = View.GONE
                   responceTextView.setTextColor(Color.parseColor("#4CAF50"))
                   val body: JsonNode = response.body()!!
                   responceTextView.text = body.toString()
               }
           }

           override fun onFailure(call: Call<JsonNode>, t: Throwable) {
               progressBar.visibility = View.GONE
               responceTextView.setTextColor(Color.parseColor("#F44336"))
               responceTextView.text = t.localizedMessage
           }

       })
    }*/
}



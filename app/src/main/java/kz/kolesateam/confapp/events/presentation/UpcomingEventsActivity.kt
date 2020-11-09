package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.Exception


const val SYNC_TEXT_COLOR = R.color.activity_upcome_events_sync_text
const val ASYNC_TEXT_COLOR = R.color.activity_upcome_events_async_text
const val FAIL_TEXT_COLOR = R.color.activity_upcome_events_fail_text

val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://37.143.8.68:2020")
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

    private fun bindViews() {
        progressBar= findViewById(R.id.progressbar)
        responseTextView = findViewById(R.id.activity_upcoming_events_response_textview)
        loadDataButtonSync = findViewById(R.id.activity_upcoming_events_sync_btn)
        loadDataButtonAsync = findViewById(R.id.activity_upcoming_events_async_btn)
        loadDataButtonSync.setOnClickListener() {
            progressBar.visibility = View.VISIBLE
            loadApiDataSync()

        }

            loadDataButtonAsync.setOnClickListener(){
               progressBar.visibility = View.VISIBLE
                loadApiDataAsync()

            }
    }


    private fun loadApiDataSync() {

        Thread {
            try {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        responseTextView.setTextColor(ContextCompat.getColor(this, SYNC_TEXT_COLOR))
                        responseTextView.text = body.toString()
                        progressBar.visibility = View.INVISIBLE
                    }
                    Log.d("UpComingEventException", "2")
                }
            }catch (e: Exception){
                runOnUiThread {
                    responseTextView.text = e.localizedMessage
                    responseTextView.setTextColor(ContextCompat.getColor(this, FAIL_TEXT_COLOR))
                    progressBar.visibility = View.INVISIBLE
                }}
        }.start()
        Log.d("UpComingEventException", "1")
    }





    private fun loadApiDataAsync() {
        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {

            override fun onResponse(
                call: Call<JsonNode>,
                response: Response<JsonNode>) { // все ошибки с сервера
                if (response.isSuccessful) {
                    responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, ASYNC_TEXT_COLOR))
                    val body: JsonNode = response.body()!!
                    responseTextView.text = body.toString()
                    progressBar.visibility = View.INVISIBLE
                }
            }
            override fun onFailure(//ошибки, которые произошли до того, как запрос дошел до бека(плохая сеть, долгий запрос)
                call: Call<JsonNode>,
                t: Throwable
            ) {
                responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, FAIL_TEXT_COLOR))
                responseTextView.text = t.localizedMessage
                progressBar.visibility = View.INVISIBLE
            }

        })
    }
}
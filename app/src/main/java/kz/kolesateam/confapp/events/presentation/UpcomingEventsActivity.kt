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
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        responseTextView.setTextColor(ContextCompat.getColor(this, R.color.activity_upcome_events_sync_text))
                        responseTextView.text = body.toString()
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

    private fun loadApiDataAsync() {
        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {

            override fun onResponse(
                    call: Call<JsonNode>,
                    response: Response<JsonNode>) {
                if (response.isSuccessful) {
                    responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, R.color.activity_upcome_events_async_text))
                    val body: JsonNode = response.body()!!
                    responseTextView.text = body.toString()
                    progressBar.gone()
                }
            }

            override fun onFailure(
                    call: Call<JsonNode>,
                    t: Throwable
            ) {
                responseTextView.setTextColor(ContextCompat.getColor(this@UpcomingEventsActivity, R.color.activity_upcome_events_fail_text))
                responseTextView.text = t.localizedMessage
                progressBar.gone()
            }
        })
    }
}
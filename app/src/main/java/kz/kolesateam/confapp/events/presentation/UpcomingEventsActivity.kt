package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = " http://37.143.8.68:2020"

val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(JacksonConverterFactory.create())
    .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var responseTextView: TextView
    private lateinit var progressBar: ProgressBar

    private val branchAdapter: BranchAdapter = BranchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()

        loadApiData()
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_upcoming_events_recyclerview)
        progressBar = findViewById(R.id.progressbar)
        recyclerView.adapter = branchAdapter
    }

    private fun loadApiData() {
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {

            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>
            ) {
                if (response.isSuccessful) {
                    branchAdapter.setList(response.body()!!)
                    progressBar.gone()
                }
            }

            override fun onFailure(
                call: Call<List<BranchApiData>>,
                t: Throwable
            ) {
                responseTextView.setTextColor(
                    ContextCompat.getColor(
                        this@UpcomingEventsActivity,
                        R.color.activity_upcome_events_fail_text
                    )
                )
                responseTextView.text = t.localizedMessage
                progressBar.gone()
            }
        })
    }
}
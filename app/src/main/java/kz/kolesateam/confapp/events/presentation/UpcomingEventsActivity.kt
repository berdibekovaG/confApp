package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.events.presentation.view.UpcomingClickListener
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

    private val branchAdapter: BranchAdapter = BranchAdapter(getEventClickListener())

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

    private fun loadApiData() {
        //поставить прогресс бар
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {

            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>
            ) {
                if (response.isSuccessful) {
                    val upcomingEventListItemList: MutableList<UpcomingEventListItem> = mutableListOf()
                    val headerListItem:UpcomingEventListItem  = UpcomingEventListItem(
                        type = 1,
                    data = "Гаухар"
                    )

                    // в response.body() лежит список branchApiData объектов
                    // создаем новый лист, переконвертируем в новый тип upComingEventListItem
                    val branchListItemList: List<UpcomingEventListItem> = response.body()!!.map { branchApiData ->
                        UpcomingEventListItem(
                            type = 2,
                            data = branchApiData
                        )
                    }

                    //сформировали новый список где первый - header
                    upcomingEventListItemList.add(headerListItem)
                    upcomingEventListItemList.addAll(branchListItemList)

                    branchAdapter.setList(upcomingEventListItemList)
                    progressBar.gone()
                }
            }

            override fun onFailure(
                call: Call<List<BranchApiData>>,
                t: Throwable
            ) {
                responseTextView.text = t.localizedMessage
                progressBar.gone()
            }
        })
    }


    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_upcoming_events_recyclerview)
        progressBar = findViewById(R.id.progressbar)
        recyclerView.adapter = branchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this,
        LinearLayoutManager.VERTICAL,
        false,

            )
    }
    private fun getEventClickListener() : UpcomingClickListener = object : UpcomingClickListener {
        override fun onEventClick(branchId: String, eventId: String) {
            Toast.makeText(this@UpcomingEventsActivity,
                "Branch: $branchId, Event: $eventId",Toast.LENGTH_SHORT
            ).show()
        }
    }

}
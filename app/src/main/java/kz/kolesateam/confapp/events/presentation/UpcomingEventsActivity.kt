package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.data.network.apiClient
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.events.presentation.view.UpcomingClickListener
import kz.kolesateam.confapp.hello.presentation.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.hello.presentation.USER_NAME_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun saveUserName(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(USER_NAME_KEY, "User").toString()
    }

    private fun loadApiData() {
        progressBar.show()
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {

            override fun onResponse(
                    call: Call<List<BranchApiData>>,
                    response: Response<List<BranchApiData>>
            ) {
                if (response.isSuccessful) {
                    val upcomingEventListItemList: MutableList<UpcomingEventListItem> =
                            mutableListOf()
                    val headerListItem: UpcomingEventListItem = UpcomingEventListItem(
                            type = 1,
                            data = getString(R.string.hello_user_fmt, saveUserName())
                    )
                    // в response.body() лежит список branchApiData объектов
                    // создаем новый лист, переконвертируем в новый тип upComingEventListItem
                    val branchListItemList: List<UpcomingEventListItem> =
                            response.body()!!.map { branchApiData ->
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
        recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false,
        )
    }

    private fun getEventClickListener(): UpcomingClickListener = object : UpcomingClickListener {

        override fun onBranchClickListener(branchTitle: String) {
            Toast.makeText(
                    this@UpcomingEventsActivity,
                    "Branch: $branchTitle", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onEventClickListenerCurrent(eventTitle: String) {
            Toast.makeText(
                    this@UpcomingEventsActivity,
                    "Event Title: $eventTitle", Toast.LENGTH_SHORT
            ).show()
        }


        override fun onEventClickListenerNext(eventTitle: String) {
            Toast.makeText(
                    this@UpcomingEventsActivity,
                    "Event Title2: $eventTitle", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFavoriteClickListenerCurrent(image: ImageView, eventId: String) {
            Toast.makeText(
                    this@UpcomingEventsActivity,
                    "нажато сердечко", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFavoriteClickListenerNext(image: ImageView, eventId: String) {
            Toast.makeText(
                    this@UpcomingEventsActivity,
                    "нажато сердечко 2", Toast.LENGTH_SHORT
            ).show()
        }
    }
}

package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.MEMORY_DATA_SOURCE
import kz.kolesateam.confapp.events.data.dataSource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.data.network.apiClient
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.events.presentation.view.gone
import kz.kolesateam.confapp.events.presentation.view.show
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val DEFAULT_USER_NAME = "Гость"

class UpcomingEventsActivity(
) : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var responseTextView: TextView
    private lateinit var progressBar: ProgressBar


    private val branchAdapter: BranchAdapter = BranchAdapter(getEventClickListener())

    private val userNameDataSource : UserNameDataSource by inject(named(MEMORY_DATA_SOURCE))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        loadApiData()
    }

    fun saveUserName(): String {
        return userNameDataSource.getUserName() ?: DEFAULT_USER_NAME
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
                    upcomingEventListItemList.addAll(listOf(headerListItem) + branchListItemList)

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
        override fun onBranchClick(title: String) {
            Toast.makeText(
                this@UpcomingEventsActivity,
                "Branch: $title", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onEventClick(title: String) {
            Toast.makeText(
                this@UpcomingEventsActivity,
                "Event Title: $title", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFavoriteClick() {
            Toast.makeText(
                this@UpcomingEventsActivity,
                "нажато сердечко", Toast.LENGTH_SHORT
            ).show()
        }
    }
}

package kz.kolesateam.confapp.events.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.AllEventsActivity
import kz.kolesateam.confapp.di.UpcomingEventsViewModel
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ProgressState
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingEventsActivity(
) : AppCompatActivity() {

    private val upcomingEventsViewModule : UpcomingEventsViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val branchAdapter: BranchAdapter = BranchAdapter(getEventClickListener())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()
}

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_upcoming_events_recyclerview)
        progressBar = findViewById(R.id.progressbar)
        recyclerView.adapter = branchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        observeUpcomingEventsViewModule()
        upcomingEventsViewModule.onStart()
    }

    private fun observeUpcomingEventsViewModule() {
        upcomingEventsViewModule.getProgressLiveData().observe(this,:: handleProgressBarState)
        upcomingEventsViewModule.geUpcomingEventLiveData().observe(this,:: showResult)
        upcomingEventsViewModule.getErrorEventLiveData().observe(this,::showError)
    }

    private fun showResult(upcomingEventList: List<UpcomingEventListItem>){
        branchAdapter.setList(upcomingEventList)
    }
    private fun showError(error: Exception){
        Toast.makeText(
            this@UpcomingEventsActivity,
            "Ошибка при загрузке", Toast.LENGTH_SHORT
        ).show()
    }

    private fun getEventClickListener(): UpcomingClickListener = object : UpcomingClickListener {
        override fun onBranchClick(title: String) {
            startingAllEventsActivity()
        }

        override fun onEventClick(title: String) {
            Toast.makeText(
                this@UpcomingEventsActivity,
                "Branch: $title", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFavoriteClick(eventTitle: EventApiData) {
            Toast.makeText(
                this@UpcomingEventsActivity,
                "нажато сердечко", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFavoriteClick(eventTitle: Unit) {
            Toast.makeText(
                this@UpcomingEventsActivity,
                "нажато сердечко", Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun handleProgressBarState(progressState: ProgressState)
    {
        progressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun startingAllEventsActivity() {
        val intent = Intent(this, AllEventsActivity::class.java)
        startActivity(intent)
    }
}

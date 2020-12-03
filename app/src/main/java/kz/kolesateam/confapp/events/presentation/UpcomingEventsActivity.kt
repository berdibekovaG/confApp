package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.UpcomingEventsViewModel
import kz.kolesateam.confapp.events.data.models.ProgressState
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Error

class UpcomingEventsActivity(
) : AppCompatActivity() {

    private  lateinit var upcomingEventsViewModule : UpcomingEventsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val branchAdapter: BranchAdapter = BranchAdapter(getEventClickListener())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
       upcomingEventsViewModule = getViewModel()

        bindViews()
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
        observeUpcomingEventsViewModule()
        upcomingEventsViewModule?.onStart()
    }

    private fun observeUpcomingEventsViewModule() {
        upcomingEventsViewModule?.getProgressLiveData().observe(this,:: handleProgressBarState)
        upcomingEventsViewModule?.geUpcomingEventLiveData().observe(this,:: showResult)
        upcomingEventsViewModule?.getErrorEventLiveData().observe(this,::showError)
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
    private fun handleProgressBarState(progressState: ProgressState)
    {
        progressBar.isVisible = progressState is ProgressState.Loading
    }
}

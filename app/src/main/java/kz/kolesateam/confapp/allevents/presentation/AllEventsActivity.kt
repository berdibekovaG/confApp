package kz.kolesateam.confapp.allevents.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.AllEventsViewModel
import kz.kolesateam.confapp.events.data.models.ProgressState
import kz.kolesateam.confapp.events.presentation.UpcomingClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllEventsActivity: AppCompatActivity() {

    private val allEventsViewModel: AllEventsViewModel by viewModel()
    private val allEventsAdapter = AllEventsAdapter(getEventClickListener())

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        bindViews()

//        favoritesButton.setOnClickListener {
//        }

        val branchId: Int = intent.getIntExtra("branch_id", 0)
        observeAllEventsViewModel()
        allEventsViewModel.onStart()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_all_events_recycler_view)
        progressBar = findViewById(R.id.progressbar)
        recyclerView.adapter = allEventsAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false,
        )
        observeAllEventsViewModel()
        allEventsViewModel.onStart()


    }

    private fun observeAllEventsViewModel() {
        allEventsViewModel.getProgressLiveData().observe(this, {
            handleProgressBarState(it)
        })
        allEventsViewModel.getAllUpcomingEventLiveData().observe(this, {
            allEventsAdapter.setList(it)
        })
    }

    private fun handleProgressBarState(
        progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }
    private fun getEventClickListener(): UpcomingClickListener = object : UpcomingClickListener {

        override fun onBranchClick(title: String) {
            Toast.makeText(
                this@AllEventsActivity,
                "Branch: $title", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onEventClick(title: String) {

        }

        override fun onFavoriteClick() {

        }

    }
}


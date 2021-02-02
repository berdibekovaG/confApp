package kz.kolesateam.confapp.allevents.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.threetenabp.AndroidThreeTen
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.AllEventsViewModel
import kz.kolesateam.confapp.event_details.EventDetailsRouter
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ProgressState
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.UpcomingClickListener
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllEventsActivity : AppCompatActivity() {

    private val allEventsViewModel: AllEventsViewModel by viewModel()

    private val allEventsBranchAdapter: AllEventsAdapter = AllEventsAdapter(
        ::onEventClick,
        ::onEventCardClick,
        ::onFavoriteClick
    )

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonBack: Button
    private lateinit var favoriteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        AndroidThreeTen.init(this)
        bindViews()

        //val branchId: Int = intent.getIntExtra("branch_id", 0)
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_all_events_recycler_view)
        progressBar = findViewById(R.id.progressbar)
        buttonBack = findViewById(R.id.buttonBack)
        favoriteButton = findViewById(R.id.upcoming_events_button_favorite)

        favoriteButton.setOnClickListener{
            val favoriteEventsListActivityIntent = Intent(this, FavoriteEventsActivity::class.java)
            startActivity(favoriteEventsListActivityIntent)
        }

        recyclerView.adapter = allEventsBranchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        observeAllEventsViewModel()

        allEventsViewModel.onStart()
        goBackToUpcomingEventsActivity()
    }


    private fun goBackToUpcomingEventsActivity() {
        buttonBack.setOnClickListener{
            val upcomingEventsActivityIntent = Intent(this, UpcomingEventsActivity::class.java)
            startActivity(upcomingEventsActivityIntent)
        }
    }

    private fun observeAllEventsViewModel() {
        allEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        allEventsViewModel.getAllUpcomingEventLiveData().observe(this, ::showResult)
        allEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun handleProgressBarState(
            progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }


    private fun showResult(upcomingEventList: List<UpcomingEventListItem>){
        allEventsBranchAdapter.setList(upcomingEventList)
    }
    private fun showError(errorMessage: Exception){
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }
    private fun onEventCardClick(
        event: EventApiData
    ){
        startActivity(EventDetailsRouter().createIntent(this, event.id!!))
    }
    private fun onEventClick(
        eventTitle: Int
    ) {
        Toast.makeText(
            this, "Event: $eventTitle", Toast.LENGTH_SHORT
        ).show()
    }
    private fun onFavoriteClick(
        eventApiData: EventApiData
    ){
        allEventsViewModel.onFavoriteClick(eventApiData)
    }

}




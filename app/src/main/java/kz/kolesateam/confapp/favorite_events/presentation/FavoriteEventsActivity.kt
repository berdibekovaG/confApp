package kz.kolesateam.confapp.favorite_events.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.threetenabp.AndroidThreeTen
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.event_details.presentation.EventDetailsRouter
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.UpcomingActivityRouter
import kz.kolesateam.confapp.events.presentation.view.gone
import kz.kolesateam.confapp.events.presentation.view.show
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.favorite_events.presentation.FavoritesAdapter
import kz.kolesateam.confapp.favorite_events.presentation.FavoritesViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FAVORITE_EVENTS_EMPTY_LIST = "[]"
class FavoriteEventsActivity : AppCompatActivity() {
    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val favoritesRepository: FavoriteEventsRepository by inject()

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutForEmpty: LinearLayout
    private lateinit var favoriteButton: Button

    private val favoritesAdapter: FavoritesAdapter = FavoritesAdapter(
        ::onEventCardClick,
        ::onFavoriteImageViewClick
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_events)
        AndroidThreeTen.init(this)

        bindViews()
    }
    private fun bindViews(){
        recyclerView = findViewById(R.id.activity_favorite_events_list_recycler_view)
        recyclerView.show()
        linearLayoutForEmpty = findViewById(R.id.activity_favorite_events_list_linear_layout_for_empty)
        if (favoritesRepository.getAllFavoriteEvents()
                .toString() != FAVORITE_EVENTS_EMPTY_LIST
        ) {
            linearLayoutForEmpty.gone()
        }
        recyclerView.adapter = favoritesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        favoriteButton = findViewById(R.id.events_button_favorites_back)
        favoriteButton.setOnClickListener{
            startActivity(UpcomingActivityRouter().createIntent(this))
        }

        favoritesViewModel.onStart()
        observeAllEventsViewModel()

    }
    private fun observeAllEventsViewModel() {
        favoritesViewModel.getFavoriteEventsLiveData().observe(this, ::showResult)
    }

    private fun showResult(eventsApiDataList: List<EventApiData>) {
        favoritesAdapter.setList(eventsApiDataList)
    }

    private fun onEventCardClick(
        event: EventApiData
    ){
        startActivity(EventDetailsRouter().createIntent(this, event.id!!))
    }
    private fun onFavoriteImageViewClick(
        eventApiData: EventApiData
    ){
        favoritesViewModel.onFavoriteClick(eventApiData)
    }
}
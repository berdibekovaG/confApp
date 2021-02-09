package kz.kolesateam.confapp.event_details.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.jakewharton.threetenabp.AndroidThreeTen
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.LEADING_ZERO_FORMAT
import kz.kolesateam.confapp.allevents.presentation.TIME_PLACE_FORMAT
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ProgressState
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.events.presentation.view.gone
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.ZonedDateTime

const val DEFAULT_EVENT_ID = 0

class EventDetailsActivity : AppCompatActivity() {

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()

    private lateinit var buttonBack: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var imageViewFavorite: ImageView

    private lateinit var photoURL: ImageView
    private lateinit var fullName: TextView
    private lateinit var job: TextView
    private lateinit var time_place: TextView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var invitedSpeaker: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        AndroidThreeTen.init(this)

        val eventId = intent.getIntExtra(EVENT_ID, DEFAULT_EVENT_ID)

        bindViews()
        eventDetailsViewModel.onStart(
                eventId
        )
        observeAllEventsViewModel()
        setOnClickListeners()
    }

    private fun bindViews() {
        buttonBack = findViewById(R.id.activity_event_details_ic_arrow_back)
        progressBar = findViewById(R.id.activity_event_details_progress_bar)
        imageViewFavorite = findViewById(R.id.activity_event_details_favorite_image_vew)

        photoURL = findViewById(R.id.activity_event_details_speaker_photo)
        fullName = findViewById(R.id.activity_event_details_speaker_name)
        job = findViewById(R.id.activity_event_details_speaker_job)
        time_place = findViewById(R.id.activity_event_details_time_place)
        title = findViewById(R.id.activity_event_details_title)
        description = findViewById(R.id.activity_event_details_description)
        invitedSpeaker = findViewById(R.id.activity_event_details_speaker_invited)
    }

    private fun observeAllEventsViewModel() {
        eventDetailsViewModel.eventDetailsLiveData.observe(this, ::showEventDetails)
    }

    private fun showEventDetails(responseData: ResponseData<EventApiData, String>) {
        when (responseData) {
            is ResponseData.Success -> {
                Glide.with(this)
                        .load(responseData.result.speaker?.photoUrl)
                        .centerCrop()
                        .into(photoURL)
                fullName.text = responseData.result.speaker?.fullName
                job.text = responseData.result.speaker?.job
                time_place.text = TIME_PLACE_FORMAT.format(
                        responseData.result.startTime?.let { getEventTime(it) },
                        responseData.result.endTime?.let { getEventTime(it) },
                        responseData.result.place
                )
                title.text = responseData.result.title
                description.text = responseData.result.description
                checkSpeaker(responseData.result.speaker?.isInvited!!)

                onFavoriteImageViewClick(responseData.result)
            }
            is ResponseData.Error -> showError(responseData.error)
        }
    }

    private fun setOnClickListeners() {
        buttonBack.setOnClickListener {
            val upcomingEventsActivityIntent = Intent(this, UpcomingEventsActivity::class.java)
            startActivity(upcomingEventsActivityIntent)
        }
    }

    private fun handleProgressBarState(
            progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showError(error: String) {
        Toast.makeText(this@EventDetailsActivity, error, Toast.LENGTH_SHORT).show()
    }

    private fun onFavoriteImageViewClick(
            eventApiData: EventApiData
    ) {
        imageViewFavorite.setOnClickListener {
            eventApiData.isFavorite = !eventApiData.isFavorite
            val favoriteImageResource = getFavoriteImageResource(eventApiData.isFavorite)
            imageViewFavorite.setImageResource(favoriteImageResource)
            eventDetailsViewModel.onFavoriteClick(eventApiData)
        }
    }

    private fun checkSpeaker(
            isInvited: Boolean
    ) {
        if (!isInvited) {
            invitedSpeaker.gone()
        }
    }

    private fun getEventTime(eventDateAndTime: String): String {
        val zonedDateTime = ZonedDateTime.parse(eventDateAndTime)
        return String.format(LEADING_ZERO_FORMAT, zonedDateTime.hour, zonedDateTime.minute)
    }

    private fun getFavoriteImageResource(
            isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_filled_white
        false -> R.drawable.ic_favorite_border
    }
}

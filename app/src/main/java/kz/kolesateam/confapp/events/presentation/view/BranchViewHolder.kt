package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.LEADING_ZERO_FORMAT
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import org.threeten.bp.ZonedDateTime

const val TIME_AND_PLACE_FORMAT = "%s - %s • %s"
const val DEFAULT_NAME = "user"

class BranchViewHolder(
        view: View,
        private val eventClick: (branchId: BranchApiData) -> Unit,
        private val eventCardClick: (event: EventApiData) -> Unit,
        private val favoriteImageViewClick: (eventData: EventApiData) -> Unit
) : RecyclerView.ViewHolder(view) {

    private lateinit var currentEvent: EventApiData
    private lateinit var nextEvent: EventApiData

    private val branchTitle: TextView = view.findViewById(R.id.item_upcoming_events_branch_title)
    private val branchTitleLayout: View = view.findViewById(R.id.item_upcoming_events_branch_block)

    private val branchCurrentEvent: View = view.findViewById(R.id.item_upcoming_events_branch_current_event)
    private val currentEventDateAndPlace: TextView =
            branchCurrentEvent.findViewById(R.id.event_date_and_place_textview)
    private val currentSpeakerName: TextView =
            branchCurrentEvent.findViewById(R.id.event_speaker_name_textiew)
    private val currentSpeakersJob: TextView =
            branchCurrentEvent.findViewById(R.id.event_speakers_job_textview)
    private val currentEventTitle: TextView =
            branchCurrentEvent.findViewById(R.id.event_title_textview)
    private val currentFavoriteView: ImageView = branchCurrentEvent.findViewById(R.id.ic_favorite_imageview)


    private val branchNextEvent: View = view.findViewById(R.id.item_upcoming_events_branch_next_event)
    private val nextEventDateAndPlace: TextView =
            branchNextEvent.findViewById(R.id.event_date_and_place_textview)
    private val nextSpeakerName: TextView =
            branchNextEvent.findViewById(R.id.event_speaker_name_textiew)
    private val nextSpeakersJob: TextView =
            branchNextEvent.findViewById(R.id.event_speakers_job_textview)
    private val nextEventTitle: TextView = branchNextEvent.findViewById(R.id.event_title_textview)
    private val nextFavoriteView: ImageView = branchNextEvent.findViewById(R.id.ic_favorite_imageview)


    // выключаем первый ивент
    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state_textview).visibility =
                View.INVISIBLE
    }

    fun onBind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title

        if (branchApiData.events.isEmpty()) {
            branchCurrentEvent.visibility = View.GONE
            branchNextEvent.visibility = View.GONE
            return
        }

        currentEvent = branchApiData.events.first()

        val currentEventDateAndPlaceText = TIME_AND_PLACE_FORMAT.format(
                currentEvent?.startTime?.let { getEventTime(it) },
                currentEvent?.endTime?.let { getEventTime(it) },
                currentEvent?.place
        )

        currentEventDateAndPlace.text = currentEventDateAndPlaceText
        currentSpeakerName.text = currentEvent.speaker?.fullName ?: DEFAULT_NAME
        currentSpeakersJob.text = currentEvent.speaker?.job
        currentEventTitle.text = currentEvent.title

        nextEvent = branchApiData.events.last()
        val nextEventDateAndPlaceText = TIME_AND_PLACE_FORMAT.format(
                nextEvent?.startTime?.let { getEventTime(it) },
                nextEvent?.endTime?.let { getEventTime(it) },
                nextEvent?.place
        )
        nextEventDateAndPlace.text = nextEventDateAndPlaceText
        nextSpeakerName.text = nextEvent.speaker?.fullName ?: "noname"
        nextSpeakersJob.text = nextEvent.speaker?.job
        nextEventTitle.text = nextEvent.title

        val favoriteImageResourceCurrent = getFavoriteImageResource(currentEvent.isFavorite)
        currentFavoriteView.setImageResource(favoriteImageResourceCurrent)
        val favoriteImageResourceNext = getFavoriteImageResource(nextEvent.isFavorite)
        nextFavoriteView.setImageResource(favoriteImageResourceNext)

        branchTitleLayout.setOnClickListener {
            eventClick(
                    branchApiData
            )
        }
        branchCurrentEvent.setOnClickListener {
            eventCardClick(
                    currentEvent
            )
        }
        branchNextEvent.setOnClickListener {
            eventCardClick(
                    nextEvent
            )
        }
        currentFavoriteView.setOnClickListener {
            currentEvent.isFavorite = !currentEvent.isFavorite

            val favoriteImageResource = getFavoriteImageResource(currentEvent.isFavorite)
            currentFavoriteView.setImageResource(favoriteImageResource)

            favoriteImageViewClick(currentEvent)
        }

        nextFavoriteView.setOnClickListener {
            nextEvent.isFavorite = !nextEvent.isFavorite

            val favoriteImageResource = getFavoriteImageResource(nextEvent.isFavorite)
            nextFavoriteView.setImageResource(favoriteImageResource)

            favoriteImageViewClick(nextEvent)
        }
    }


    private fun getFavoriteImageResource(isFavorite: Boolean): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_filled_imageview
        else -> R.drawable.ic_favorite_blue
    }

    private fun getEventTime(eventDateAndTime: String): String {
        val zonedDateTime = ZonedDateTime.parse(eventDateAndTime)
        return String.format(LEADING_ZERO_FORMAT, zonedDateTime.hour, zonedDateTime.minute)
    }
}



package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.UpcomingClickListener

const val TIME_AND_PLACE_FORMAT = "%s - %s • %s"

class BranchViewHolder(
        view: View,
        private val eventClickListener: UpcomingClickListener,
) : RecyclerView.ViewHolder(view) {

    private lateinit var currentEvent: EventApiData
    private lateinit var nextEvent: EventApiData

    private val branchTitle: TextView = view.findViewById(R.id.branch_title)

    private val branchCurrentEvent: View = view.findViewById(R.id.branch_current_event)
    private val currentEventDateAndPlace: TextView =
            branchCurrentEvent.findViewById(R.id.event_date_and_place_textview)
    private val currentSpeakerName: TextView =
            branchCurrentEvent.findViewById(R.id.event_speaker_name_textiew)
    private val currentSpeakersJob: TextView =
            branchCurrentEvent.findViewById(R.id.event_speakers_job_textview)
    private val currentEventTitle: TextView =
            branchCurrentEvent.findViewById(R.id.event_title_textview)
    private val currentFavoriteView: ImageView = branchCurrentEvent.findViewById(R.id.ic_favorite_imageview)

    private val branchNextEvent: View = view.findViewById(R.id.branch_next_event)
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
                currentEvent.startTime,
                currentEvent.endTime,
                currentEvent.place,
        )

        currentEventDateAndPlace.text = currentEventDateAndPlaceText
        currentSpeakerName.text = currentEvent.speaker?.fullName ?: "noname"
        currentSpeakersJob.text = currentEvent.speaker?.job
        currentEventTitle.text = currentEvent.title

        nextEvent = branchApiData.events.last()
        val nextEventDateAndPlaceText = TIME_AND_PLACE_FORMAT.format(
                nextEvent.startTime,
                nextEvent.endTime,
                nextEvent.place,
        )
        nextEventDateAndPlace.text = nextEventDateAndPlaceText
        nextSpeakerName.text = nextEvent.speaker?.fullName ?: "noname"
        nextSpeakersJob.text = nextEvent.speaker?.job
        nextEventTitle.text = nextEvent.title

        val favoriteImageResourceCurrent = getFavoriteImageResource(currentEvent.isFavorite)
        currentFavoriteView.setImageResource(favoriteImageResourceCurrent)
        val favoriteImageResourceNext = getFavoriteImageResource(nextEvent.isFavorite)
        nextFavoriteView.setImageResource(favoriteImageResourceNext)

        initOnClickListeners(currentEvent, nextEvent, branchApiData)
    }

    fun initOnClickListeners(
            currentEvent: EventApiData,
            nextEvent: EventApiData,
            branchApiData: BranchApiData) {

        branchCurrentEvent.setOnClickListener {
            branchApiData.events.first().title?.let { it1 ->
                eventClickListener.onEventClick(
                        it1
                )
            }
        }
        branchNextEvent.setOnClickListener {
            branchApiData.events.last().title?.let { it1 ->
                eventClickListener.onEventClick(
                        it1
                )
            }
        }
        branchTitle.setOnClickListener {
            branchApiData.title?.let { it1 ->
                eventClickListener.onBranchClick(
                        it1
                )
            }
        }
        currentFavoriteView.setOnClickListener {
            currentEvent.isFavorite = !currentEvent.isFavorite

            val favoriteImageResource = getFavoriteImageResource(currentEvent.isFavorite)
            currentFavoriteView.setImageResource(favoriteImageResource)

            eventClickListener.onFavoriteClick(
                    eventClickListener.onFavoriteClick(currentEvent)
            )
        }
        nextFavoriteView.setOnClickListener {
            nextEvent.isFavorite = !nextEvent.isFavorite

            val favoriteImageResource = getFavoriteImageResource(nextEvent.isFavorite)
            nextFavoriteView.setImageResource(favoriteImageResource)
            eventClickListener.onFavoriteClick(
                    eventClickListener.onFavoriteClick(currentEvent)
            )
        }
    }

    private fun getFavoriteImageResource(isFavorite: Boolean): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_filled_imageview
        else -> R.drawable.ic_favorite_border
    }
}


package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData

class BranchViewHolder(
        view: View,
        private val eventClickListener: UpcomingClickListener,
) : RecyclerView.ViewHolder(view) {

    private val eventContainer: View = view.findViewById(R.id.branch_list_item_container)
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
    private val favoriteImageCurrent: ImageView = branchCurrentEvent.findViewById(R.id.ic_favorite_imageview)

    private val branchNextEvent: View = view.findViewById(R.id.branch_next_event)
    private val nextEventDateAndPlace: TextView =
            branchNextEvent.findViewById(R.id.event_date_and_place_textview)
    private val nextSpeakerName: TextView =
            branchNextEvent.findViewById(R.id.event_speaker_name_textiew)
    private val nextSpeakersJob: TextView =
            branchNextEvent.findViewById(R.id.event_speakers_job_textview)
    private val nextEventTitle: TextView = branchNextEvent.findViewById(R.id.event_title_textview)
    private val favoriteImageNext: ImageView = branchNextEvent.findViewById(R.id.ic_favorite_imageview)

    // выключаем первый ивент
    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state_textview).visibility =
                View.INVISIBLE
    }

    fun onBind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title
        val currentEvent: EventApiData = branchApiData.events.first()

        val currentEventDateAndPlaceText = "%s - %s • %s".format(
                currentEvent.startTime,
                currentEvent.endTime,
                currentEvent.place,
        )
        onClickListeners(branchApiData)

        currentEventDateAndPlace.text = currentEventDateAndPlaceText
        currentSpeakerName.text = currentEvent.speaker?.fullName ?: "noname"
        currentSpeakersJob.text = currentEvent.speaker?.job
        currentEventTitle.text = currentEvent.title

        val nextEvent: EventApiData = branchApiData.events.last()
        val nextEventDateAndPlaceText = "%s - %s • %s".format(
                nextEvent.startTime,
                nextEvent.endTime,
                nextEvent.place,
        )
        nextEventDateAndPlace.text = nextEventDateAndPlaceText
        nextSpeakerName.text = nextEvent.speaker?.fullName ?: "noname"
        nextSpeakersJob.text = nextEvent.speaker?.job
        nextEventTitle.text = nextEvent.title
    }

    fun onClickListeners(branchApiData: BranchApiData) {

        branchCurrentEvent.setOnClickListener {
            branchApiData.events.first().title?.let { it1 ->
                eventClickListener.onEventClickListenerCurrent(
                        it1
                )
            }
        }
        branchNextEvent.setOnClickListener {
            branchApiData.events.last().title?.let { it1 ->
                eventClickListener.onEventClickListenerNext(
                        it1
                )
            }
        }
        branchTitle.setOnClickListener {
            branchApiData.title?.let { it1 ->
                eventClickListener.onBranchClickListener(
                        it1
                )
            }
        }
        favoriteImageCurrent.setOnClickListener {
            favoriteImageCurrent.setImageResource(R.drawable.ic_favorite_filled_imageview)
        }
        favoriteImageNext.setOnClickListener {
            favoriteImageNext.setImageResource(R.drawable.ic_favorite_filled_imageview)
        }
    }
}


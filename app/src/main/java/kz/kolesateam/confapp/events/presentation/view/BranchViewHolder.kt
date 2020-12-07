package kz.kolesateam.confapp.events.presentation.view

import android.view.View
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

    private val branchNextEvent: View = view.findViewById(R.id.branch_next_event)
    private val nextEventDateAndPlace: TextView =
        branchNextEvent.findViewById(R.id.event_date_and_place_textview)
    private val nextSpeakerName: TextView =
        branchNextEvent.findViewById(R.id.event_speaker_name_textiew)
    private val nextSpeakersJob: TextView =
        branchNextEvent.findViewById(R.id.event_speakers_job_textview)
    private val nextEventTitle: TextView = branchNextEvent.findViewById(R.id.event_title_textview)

    // выключаем первый ивент
    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state_textview).visibility =
            View.INVISIBLE
    }

    fun onBind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title
        val currentEvent: EventApiData = branchApiData.events.first()

        val currentEventDateAndPlaceText = TIME_AND_PLACE_FORMAT.format(
            currentEvent.startTime,
            currentEvent.endTime,
            currentEvent.place,
        )
        initOnClickListeners(branchApiData)

        currentEventDateAndPlace.text = currentEventDateAndPlaceText
        currentSpeakerName.text = currentEvent.speaker?.fullName ?: "noname"
        currentSpeakersJob.text = currentEvent.speaker?.job
        currentEventTitle.text = currentEvent.title

        val nextEvent: EventApiData = branchApiData.events.last()
        val nextEventDateAndPlaceText = TIME_AND_PLACE_FORMAT.format(
            nextEvent.startTime,
            nextEvent.endTime,
            nextEvent.place,
        )
        nextEventDateAndPlace.text = nextEventDateAndPlaceText
        nextSpeakerName.text = nextEvent.speaker?.fullName ?: "noname"
        nextSpeakersJob.text = nextEvent.speaker?.job
        nextEventTitle.text = nextEvent.title
    }

    fun initOnClickListeners(branchApiData: BranchApiData) {

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
    }
}


package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData


class BranchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val branchCurrentEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val branchNextEvent: View = itemView.findViewById(R.id.branch_next_event)

    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)

    private val currentEventDateAndPlace: TextView =
        branchCurrentEvent.findViewById(R.id.event_date_and_place_textview)
    private val currentSpeakerName: TextView =
        branchCurrentEvent.findViewById(R.id.event_speaker_name_textiew)
    private val currentSpeakersJob: TextView =
        branchCurrentEvent.findViewById(R.id.event_speakers_job_textview)
    private val currentEventTitle: TextView =
        branchCurrentEvent.findViewById(R.id.event_title_textview)

    private val nextEventDateAndPlace: TextView =
        branchNextEvent.findViewById(R.id.event_date_and_place_textview)
    private val nextSpeakerName: TextView =
        branchNextEvent.findViewById(R.id.event_speaker_name_textiew)
    private val nextSpeakersJob: TextView =
        branchNextEvent.findViewById(R.id.event_speakers_job_textview)
    private val nextEventTitle: TextView = branchNextEvent.findViewById(R.id.event_title_textview)

    // выключаем первый ивент
    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state_textview).visibility = View.GONE
    }


    fun onBind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title
        val currentEvent: EventApiData = branchApiData.events.first()

        val currentEventDateAndPlaceText = "%s - %s • %s".format(
            currentEvent.startTime,
            currentEvent.endTime,
            currentEvent.place,
        )

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
}

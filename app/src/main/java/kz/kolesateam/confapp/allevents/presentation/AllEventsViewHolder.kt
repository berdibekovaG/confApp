package kz.kolesateam.confapp.allevents.presentation

import android.content.ClipData
import android.text.Layout
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.UpcomingClickListener
import kz.kolesateam.confapp.events.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.events.presentation.view.TIME_AND_PLACE_FORMAT

class AllEventsViewHolder(
    view: View,
    private val allEVentsClickListener: UpcomingClickListener
): BaseViewHolder<EventApiData>(view) {

    private val branchCurrentEvent: View = view.findViewById(R.id.card_view)
    private val currentEventDateAndPlace: TextView =
        branchCurrentEvent.findViewById(R.id.event_date_and_place_textview)
    private val currentSpeakerName: TextView =
        branchCurrentEvent.findViewById(R.id.event_speaker_name_textiew)
    private val currentSpeakersJob: TextView =
        branchCurrentEvent.findViewById(R.id.event_speakers_job_textview)
    private val currentEventTitle: TextView =
        branchCurrentEvent.findViewById(R.id.event_title_textview)


   override fun onBind(data: EventApiData) {
        val currentEventDateAndPlaceText = TIME_AND_PLACE_FORMAT.format(
            data.startTime,
            data.endTime,
            data.place,
        )


        currentEventDateAndPlace.text = currentEventDateAndPlaceText
        currentSpeakerName.text = data.speaker?.fullName ?: "noname"
        currentSpeakersJob.text = data.speaker?.job
        currentEventTitle.text = data.title


    }


}

package kz.kolesateam.confapp.allevents.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData
import org.threeten.bp.ZonedDateTime

const val TIME_PLACE_FORMAT = "%s - %s • %s"
const val LEADING_ZERO_FORMAT = "%02d:%02d"

class AllEventsViewHolder(
        view: View,
        private val eventClick: (eventId: Int) -> Unit,
        private val eventCardClick: (eventData: EventApiData) -> Unit,
        private val favoriteImageViewClick: (eventData: EventApiData) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val currentEventDateAndPlace: TextView =
            view.findViewById(R.id.event_date_and_place_textview)
    private val currentSpeakerName: TextView =
            view.findViewById(R.id.event_speaker_name_textiew)
    private val currentSpeakersJob: TextView =
            view.findViewById(R.id.event_speakers_job_textview)
    private val currentEventTitle: TextView =
            view.findViewById(R.id.event_title_textview)
    private val imageFavorite: ImageView = itemView.findViewById(R.id.ic_favorite_imageview)

    fun onEventBind(branchApidata: EventApiData) {
        val event: EventApiData? = branchApidata
        val currentEventDatePlaceText = TIME_PLACE_FORMAT.format(
                event?.startTime?.let { getEventTime(it) },
                event?.endTime?.let { getEventTime(it) },
                event?.place
        )

        currentEventDateAndPlace.text = currentEventDatePlaceText
        currentSpeakerName.text = branchApidata.speaker?.fullName ?: "noname"
        currentSpeakersJob.text = branchApidata.speaker?.job
        currentEventTitle.text = branchApidata.title

        currentEventTitle.setOnClickListener {
            eventCardClick(
                    event!!
            )
        }
        imageFavorite.setOnClickListener {
            event?.isFavorite = !event?.isFavorite!!
            val favoriteImageResource = getFavoriteImageResource(event.isFavorite)
            imageFavorite.setImageResource(favoriteImageResource)

            favoriteImageViewClick(event)
        }
    }

    private fun getEventTime(eventDateAndTime: String): String {
        val zonedDateTime = ZonedDateTime.parse(eventDateAndTime)
        return String.format(LEADING_ZERO_FORMAT, zonedDateTime.hour, zonedDateTime.minute)
    }

    private fun getFavoriteImageResource(
            isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_filled_imageview
        false -> R.drawable.ic_favorite_blue
    }
}

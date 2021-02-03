package kz.kolesateam.confapp.favorite_events.presentation

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.LEADING_ZERO_FORMAT
import kz.kolesateam.confapp.allevents.presentation.TIME_PLACE_FORMAT
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.DEFAULT_NAME
import kz.kolesateam.confapp.events.presentation.view.gone
import org.threeten.bp.ZonedDateTime

class FavoritesViewHolder(
    itemView: View,
    private val eventCardClick: (eventData: EventApiData) -> Unit,
    private val favoriteImageViewClick: (eventData: EventApiData) -> Unit
): RecyclerView.ViewHolder(itemView) {
    private val eventDatePlace: TextView = itemView.findViewById(R.id.event_date_and_place_textview)
    private val imageViewFavorite: ImageView = itemView.findViewById(R.id.ic_favorite_imageview)
    private val speakerName: TextView = itemView.findViewById(R.id.event_speaker_name_textiew)
    private val speakerJob: TextView = itemView.findViewById(R.id.event_speakers_job_textview)
    private val eventTitle: TextView = itemView.findViewById(R.id.event_title_textview)

    init {
        itemView.findViewById<TextView>(R.id.event_state_textview).gone()
    }

    fun onBind(branchApiData: EventApiData){

        val event: EventApiData? = branchApiData
        val currentEventDatePlaceText = TIME_PLACE_FORMAT.format(
            event?.startTime?.let { getEventTime(it) },
            event?.endTime?.let { getEventTime(it) },
            event?.place
        )
        eventDatePlace.text = currentEventDatePlaceText
        speakerName.text = event?.speaker?.fullName ?: DEFAULT_NAME
        speakerJob.text = event?.speaker?.job
        eventTitle.text = event?.title

        eventTitle.setOnClickListener {
            eventCardClick(
                event!!
            )
        }
        imageViewFavorite.setOnClickListener{
            event?.isFavorite = !event?.isFavorite!!

            val favoriteImageResource = getFavoriteImageResource(event.isFavorite)
            imageViewFavorite.setImageResource(favoriteImageResource)

            favoriteImageViewClick(event)
        }
    }
    private fun getFavoriteImageResource(
        isFavorite: Boolean
    ): Int = when (isFavorite){
        true -> R.drawable.ic_favorite_filled_white
        false -> R.drawable.ic_favorite_border
    }

    private fun getEventTime(eventDateAndTime: String): String {
        val zonedDateTime = ZonedDateTime.parse(eventDateAndTime)
        return String.format(LEADING_ZERO_FORMAT, zonedDateTime.hour, zonedDateTime.minute)
    }
}
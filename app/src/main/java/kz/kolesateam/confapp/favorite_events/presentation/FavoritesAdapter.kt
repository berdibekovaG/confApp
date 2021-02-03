package kz.kolesateam.confapp.favorite_events.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData

class FavoritesAdapter(
    private val eventCardClick: (eventData: EventApiData) ->Unit,
    private val favoriteImageViewClick: (eventData: EventApiData) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val favoriteEventsDataList: MutableList<EventApiData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoritesViewHolder(
            View.inflate(parent.context, R.layout.event_card_layout, null),
            eventCardClick = eventCardClick,
            favoriteImageViewClick = favoriteImageViewClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoritesViewHolder){
        holder.onBind(favoriteEventsDataList[position])
    }
    }

    override fun getItemCount(): Int {
        return favoriteEventsDataList.size
    }

    fun setList(favoriteEventsList: List<EventApiData>) {
        this.favoriteEventsDataList.clear()
        this.favoriteEventsDataList.addAll(favoriteEventsList)

        notifyDataSetChanged()
    }
}
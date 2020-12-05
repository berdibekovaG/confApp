package kz.kolesateam.confapp.allevents.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.UpcomingClickListener
import kz.kolesateam.confapp.events.presentation.view.BranchViewHolder
import kz.kolesateam.confapp.events.presentation.view.HeadVIewHolder

class AllEventsAdapter(
   private val allEventsClickListeners: UpcomingClickListener
) : RecyclerView.Adapter<AllEventsViewHolder>() {

    private val dataList: MutableList<EventApiData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllEventsViewHolder =
        AllEventsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.all_branch_item,
                parent,
                false
            ),
            allEventsClickListeners
        )

    override fun onBindViewHolder(holder: AllEventsViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setList(eventList: List<EventApiData>) {
        dataList.clear()
        dataList.addAll(eventList)
        notifyDataSetChanged()
    }
}
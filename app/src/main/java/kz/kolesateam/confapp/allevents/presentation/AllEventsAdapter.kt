package com.example.all_events.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.AllEventsHeaderViewHolder
import kz.kolesateam.confapp.allevents.presentation.AllEventsViewHolder
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem

class AllEventsBranchAdapter(
        private val eventClick: (branchId: Int) -> Unit,
        private val eventCardClick: (eventData: EventApiData) -> Unit,
        private val favoriteImageViewClick: (eventData: EventApiData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<UpcomingEventListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            1 -> AllEventsHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_all_events, parent, false))
            else -> AllEventsViewHolder(View.inflate(parent.context, R.layout.all_branch_item, null),
                    eventClick = eventClick,
                    eventCardClick = eventCardClick,
                    favoriteImageViewClick = favoriteImageViewClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AllEventsHeaderViewHolder) {
            holder.onBind(dataList[position].data as BranchApiData)
        }
        if (holder is AllEventsViewHolder) {
            holder.onEventBind(dataList[position].data as EventApiData)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    fun setList(branchApiDataList: List<UpcomingEventListItem>) {
        dataList.clear()
        dataList.addAll(branchApiDataList)
        notifyDataSetChanged()
    }
}
package kz.kolesateam.confapp.allevents.presentation


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData

class AllEventsHeaderViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    private val branchNameTextView : TextView =item.findViewById(R.id.AllUpcomingEventsActivity_header_branch_title)

    fun onBind(branch: BranchApiData) {
        branchNameTextView.text = branch.title as String
    }
}
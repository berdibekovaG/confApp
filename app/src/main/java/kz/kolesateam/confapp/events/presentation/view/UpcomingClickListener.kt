package kz.kolesateam.confapp.events.presentation.view

import android.widget.ImageView

interface UpcomingClickListener {

    fun onBranchClickListener(branchTitle: String)
    fun onEventClickListener(eventTitle: String)
    fun onFavoriteClickListener(image: ImageView, eventId: String)


}
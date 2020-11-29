package kz.kolesateam.confapp.events.presentation.view

import android.widget.ImageView

interface UpcomingClickListener {

    fun onBranchClickListener(branchTitle: String)
    fun onEventClickListenerCurrent(eventTitle: String)
    fun onEventClickListenerNext(eventTitle: String)
    fun onFavoriteClickListenerCurrent(image: ImageView, eventId: String)
    fun onFavoriteClickListenerNext(image: ImageView, eventId: String)


}
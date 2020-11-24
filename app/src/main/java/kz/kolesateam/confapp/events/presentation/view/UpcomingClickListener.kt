package kz.kolesateam.confapp.events.presentation.view

import android.widget.ImageView

interface UpcomingClickListener {

    fun onBranchClickListener(branchId: String)
    fun onEventClickListener(branchId:String, eventId: String)
    fun onFavoriteClickListener(image: ImageView, eventId: String)


}
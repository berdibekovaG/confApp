package kz.kolesateam.confapp.events.presentation.view

interface UpcomingClickListener {

    fun onEventClick(
        branchId: String,
        eventId: String
    )


}
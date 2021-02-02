package kz.kolesateam.confapp.event_details

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsActivity

const val EVENT_ID = "EVENT_ID"

class EventDetailsRouter {
    fun createIntent(
        context: Context,
        eventId: Int
    ) = Intent(context, FavoriteEventsActivity::class.java).apply {
        putExtra(EVENT_ID, eventId)
    }

    fun createIntent2(){}
//        context: Context
//    ): Intent = Intent(context, EventDetailsActivity::class.java)

    fun createIntentForNotification(){}
//        context: Context,
//        eventId: Int
//    ): Intent = createIntent2(context).apply {
//        putExtra(EVENT_ID, eventId)
//    }
}
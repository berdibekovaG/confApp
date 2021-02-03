package kz.kolesateam.confapp.event_details.presentation

import android.content.Context
import android.content.Intent

const val EVENT_ID = "EVENT_ID"

class EventDetailsRouter {
    fun createIntent(
        context: Context,
        eventId: Int
    ) = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(EVENT_ID, eventId)
    }

    fun createIntent2(
       context: Context
  ): Intent = Intent(context, EventDetailsActivity::class.java)

    fun createIntentForNotification(
        context: Context,
        eventId: Int
    ): Intent = createIntent2(context).apply {
        putExtra(EVENT_ID, eventId)
    }
}
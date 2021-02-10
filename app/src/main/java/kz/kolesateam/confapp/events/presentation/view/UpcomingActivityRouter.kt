package kz.kolesateam.confapp.events.presentation.view

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

val PUSH_NOTIFICATION_MESSAGE = "push_message"

class UpcomingActivityRouter {

    fun createIntent(
            context: Context
    ): Intent = Intent(context, UpcomingEventsActivity::class.java)

    fun createIntentForNotification(
            context: Context,
            messageFromPush: String
    ): Intent = createIntent(context).apply {
        putExtra(PUSH_NOTIFICATION_MESSAGE, messageFromPush)
    }
}
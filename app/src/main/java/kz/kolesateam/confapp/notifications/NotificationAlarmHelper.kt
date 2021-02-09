package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.data.models.EventApiData
import org.threeten.bp.ZonedDateTime

const val NOTIFICATION_CONTENT_KEY = "notification_title"
const val NOTIFICATION_EVENT_ID_KEY = "event_id"

class NotificationAlarmHelper(
        private val application: Application
) {
    fun createNotificationAlarm(
            eventApiData: EventApiData
    ) {
        val content = eventApiData.title

        val alarmManager: AlarmManager? = application.getSystemService(
                Context.ALARM_SERVICE
        ) as? AlarmManager
        val pendingIntent: PendingIntent =
                Intent(application, NotificationAlarmBroadcastReceiver::class.java).apply {
                    putExtra(NOTIFICATION_CONTENT_KEY, content)
                    putExtra(NOTIFICATION_EVENT_ID_KEY, eventApiData.id)
                }.let {
                    PendingIntent.getBroadcast(application, eventApiData.id!!, it, PendingIntent.FLAG_ONE_SHOT)
                }

        alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                getStartTime(getDateTimeByFormat(eventApiData.startTime!!)),
                pendingIntent
        )
    }

    fun cancelNotificationAlarm(
            eventApiData: EventApiData
    ) {
        val alarmManager: AlarmManager? = application.getSystemService(
                Context.ALARM_SERVICE
        ) as? AlarmManager

        val requestCode = eventApiData.id as Int
        val pendingIntent: PendingIntent = Intent(application,
                NotificationAlarmBroadcastReceiver::class.java).let {
            PendingIntent.getBroadcast(application, eventApiData.id, it,
                    PendingIntent.FLAG_ONE_SHOT
            )
        }
        alarmManager?.cancel(pendingIntent)

    }

    private fun getStartTime(
            startTime: ZonedDateTime
    ): Long = startTime.toEpochSecond() * 1000 - 300000
}
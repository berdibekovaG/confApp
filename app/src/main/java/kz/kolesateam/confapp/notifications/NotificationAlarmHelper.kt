package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmHelper(
        private val application: Application
) {
    fun createNotificationAlarm(
            content: String
    ) {
        val alarmManager: AlarmManager? = application.getSystemService(
                Context.ALARM_SERVICE
        ) as? AlarmManager
        val intent= Intent(application, NotificationAlarmBroadcastReceiver::class.java).apply {
            putExtra(NOTIFICATION_CONTENT_KEY, content)
        }
        val pendingIntent: PendingIntent =PendingIntent.getBroadcast(application, 0, intent, 0)

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.YEAR, 2020)
        calendar.set(Calendar.MONTH, 12)
        calendar.set(Calendar.DAY_OF_MONTH, 20)
        calendar.set(Calendar.HOUR_OF_DAY, 1)
        calendar.set(Calendar.MINUTE, 53)

        alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
        )
    }
}
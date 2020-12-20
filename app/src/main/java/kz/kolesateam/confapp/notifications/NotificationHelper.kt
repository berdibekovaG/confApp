package kz.kolesateam.confapp.notifications

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R

object NotificationHelper {

    private lateinit var application: Application
    private var notificationCounter: Int = 0

    fun init(application: Application) {
        this.application = application
        initChannel()
    }

    fun sendNotification(
            title: String,
            content: String
    ) {
        val notification: Notification = getNotification(
                title = title,
                content = content
        )
        NotificationManagerCompat.from(application).notify(
                notificationCounter++,
                notification
        )
    }

    private fun getNotification(
            title: String,
            content: String
    ): Notification = NotificationCompat.Builder(
            application, "favorite_notification_channel"
    ).setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_favorite_filled_imageview)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()


    private fun initChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channelId: String = "favorite_notification_channel"
        val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
                channelId, channelId, importance
        )
        val notificationManager: NotificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
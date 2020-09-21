package ru.trinitydigital.fitnes.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.ui.MyLocationForegroundService
import ru.trinitydigital.fitnes.ui.MyLocationForegroundService.Companion.STOP_SERVICE_ACTION

object NotificationHelper {
    private const val CHANNEL_ID = "CHANNEL_ID"

    fun createNotification(context: Context): Notification? {

        createNotificationChannel(context)

        val intent = Intent(context, MyLocationForegroundService::class.java)

        intent.action = STOP_SERVICE_ACTION

        val pIntent = PendingIntent.getService(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_account_balance_wallet_24)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .addAction(R.drawable.ic_baseline_account_balance_wallet_24, "stop", pIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }


    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "asdasdasdasd"
            val descriptionText = "1212121212"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
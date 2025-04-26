package ru.effectivem.a4androidsdk.z2workManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.effectivem.a4androidsdk.R

class MyBatteryWorkManager(
    context: Context,
    workerParams: WorkerParameters
) : Worker(
    context = context,
    workerParams = workerParams,
) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "charging_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Charging Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(applicationContext.getString(R.string.charge_title))
            .setContentText(applicationContext.getString(R.string.charge_message_charging))
            .setSmallIcon(android.R.drawable.ic_lock_idle_charging)
            .build()

        notificationManager.notify(1, notification)
    }
}
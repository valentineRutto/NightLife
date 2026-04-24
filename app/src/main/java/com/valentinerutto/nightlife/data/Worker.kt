package com.valentinerutto.nightlife.data

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class NotificationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params),
    KoinComponent{

    private val eventRepository: EventRepository by inject()

    override suspend fun doWork(): Result {
        val eventID = inputData.getString("eventID")
        val eventTitle = inputData.getString("eventTitle") ?: "Your Event"

        notify(
            title = "Booking confirmed! 🎉",
            body  = "Your ticket for ${eventTitle} is ready.",
            id    = eventTitle.hashCode(),
            channel = CHANNEL_BOOKINGS,
            channelName = "Bookings",
        )

        return Result.success()

    }

    private fun notify(title: String, body: String, id: Int, channel: String, channelName: String) {
        val nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(
                NotificationChannel(
                    channel,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        nm.notify(id, NotificationCompat.Builder(applicationContext, channel)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build())
    }

    companion object {
        const val KEY_BOOKING_ID = "eventTitle"
        const val CHANNEL_BOOKINGS = "bookings"

        fun schedule(context: Context, bookingId: String) {
            val request = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(workDataOf(KEY_BOOKING_ID to bookingId))
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork("booking_poll_$bookingId", ExistingWorkPolicy.KEEP, request)
        }

}
}
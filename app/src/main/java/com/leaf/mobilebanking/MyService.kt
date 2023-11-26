package com.leaf.mobilebanking

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.leaf.mobilebanking.data.preferences.Settings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyService : Service() {

    companion object{
        var code: String? = null
    }
    override fun onCreate() {

        val notification = Notification.Builder(applicationContext)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Your code is $code")
            .setContentText("Do not give this code to anyone")

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createNotificationChannel())
            notification.setChannelId("channel")
        }

        val notificationBuilder = notification.build()
        notificationManager.notify(1, notificationBuilder)
        startForeground(1, notificationBuilder)

    }
    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() = NotificationChannel("channel", "Verify", NotificationManager.IMPORTANCE_HIGH)

}
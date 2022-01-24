package millich.michael.bordcasttest

import android.annotation.SuppressLint
import android.app.Notification.FOREGROUND_SERVICE_IMMEDIATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import millich.michael.bordcasttest.databse.UnlockDatabase

class MyTestService : Service() {
lateinit var database: UnlockDatabase
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        val application = requireNotNull(this).application
        database = UnlockDatabase.getInstance(application)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotificationAndStartForeground("MISHA's notification" , "# unlocks")
        registerReceiver(UnlockBroadcastReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(UnlockBroadcastReceiver)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotificationAndStartForeground(title: String, message: String) {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID_1,
            CHANNEL_NAME_1,
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = CHANNEL_DESCRIPTION_1
        mNotificationManager.createNotificationChannel(channel)

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
            .setContentTitle(title) // title for notification
            .setContentText(message)// message for notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        mNotificationManager.notify(ONGOING_NOTIFICATION_ID,notification)
        //startForeground(ONGOING_NOTIFICATION_ID,notification)
    }
}
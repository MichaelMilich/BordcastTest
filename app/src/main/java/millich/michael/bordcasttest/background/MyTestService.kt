package millich.michael.bordcasttest.background

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import millich.michael.bordcasttest.MainActivity
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.databse.UnlockDatabase
import millich.michael.bordcasttest.databse.UnlockDatabaseDAO

class MyTestService : Service() {

inner class LocalBinder : Binder() {
   fun getService() : MyTestService =this@MyTestService
}
private val binder = LocalBinder()

lateinit var database: UnlockDatabaseDAO
var isServiceRunning =false // if the service is already running, don't create another broadcast receiver and don't show new notifications
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        val application = requireNotNull(this).application
        database = UnlockDatabase.getInstance(application).unlockDatabaseDAO
        isServiceRunning=false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(STOP_MY_SERVICE == intent!!.action)
        {
            Log.i("Test", "Called to stop the service")
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.cancel(ONGOING_NOTIFICATION_ID)
            stopSelf()
        }

        if(isServiceRunning)
            return super.onStartCommand(intent, flags, startId)

        runBlocking { launch {
            val unlockCount =database.getTodayUnlocksCountAfterTimeNoLiveData(getCurrentDateInMilli())
            showNotificationAndStartForeground(" $unlockCount  unlocks today" , "")
        } }

        registerReceiver(UnlockBroadcastReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        isServiceRunning=true
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        isServiceRunning=false
        stopForeground(true)
        unregisterReceiver(UnlockBroadcastReceiver)
        super.onDestroy()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    suspend fun showNotificationAndStartForeground(title: String, message: String) {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID_1,
            CHANNEL_NAME_1,
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = CHANNEL_DESCRIPTION_1
        mNotificationManager.createNotificationChannel(channel)

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val stopIntent = Intent(applicationContext, MyTestService::class.java)
        stopIntent.action= STOP_MY_SERVICE
        val pendingStopIntent = PendingIntent.getService(applicationContext,0,stopIntent,PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
            .setContentTitle(title) // title for notification
            .setContentText(message)// message for notification
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_launcher_background,applicationContext.resources.getString(R.string.stop_service),pendingStopIntent)
            .build()

        startForeground(ONGOING_NOTIFICATION_ID,notification)
    }
}
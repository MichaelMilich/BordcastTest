package millich.michael.bordcasttest

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
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
        registerReceiver(UnlockBroadcastReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        return super.onStartCommand(intent, flags, startId)
    }
}
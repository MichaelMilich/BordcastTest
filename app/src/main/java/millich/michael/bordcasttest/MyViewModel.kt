package millich.michael.bordcasttest

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.SyncStateContract
import android.util.Log
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
lateinit var _intent : Intent
var count =0
    fun start(context: Context){
            _intent = Intent(context, MyTestService::class.java)
            _intent.action = START_MY_SERVICE
            context.startForegroundService(_intent)
    }
    fun stop(context: Context){
            _intent = Intent(context, MyTestService::class.java)
            _intent.action = STOP_MY_SERVICE
            context.stopService(_intent)

    }
}
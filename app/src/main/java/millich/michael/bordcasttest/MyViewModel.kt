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
        Log.i("Test", "ViewModel count click = $count")
        if(count ==0) {
            count ++
            _intent = Intent(context, MyTestService::class.java)
            _intent.action = START_MY_SERVICE
            context.startService(_intent)
            //context.startForegroundService(_intent)

        }
    }
    fun stop(context: Context){
        if(count >0) {
            count--
            _intent = Intent(context, MyTestService::class.java)
            _intent.action = STOP_MY_SERVICE
            context.stopService(_intent)
        }

    }
}
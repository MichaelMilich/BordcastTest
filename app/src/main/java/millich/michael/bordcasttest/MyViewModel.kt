package millich.michael.bordcasttest

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
lateinit var _intent : Intent
var count =0
    fun start(context: Context){
        if(count ==0) {
            count ++
            _intent = Intent(context, MyTestService::class.java)
            context.startService(_intent)
        }
    }
    fun stop(context: Context){
        context.stopService(_intent)
    }
}
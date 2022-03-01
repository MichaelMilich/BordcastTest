package millich.michael.bordcasttest.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import millich.michael.bordcasttest.background.*
import millich.michael.bordcasttest.databse.UnlockDatabaseDAO
import millich.michael.bordcasttest.databse.UnlockEvent
import java.util.*

class HomeViewModel(val database: UnlockDatabaseDAO,application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _buttonsVisible= MutableLiveData<Boolean>()
    val buttonVisible : LiveData<Boolean>
    get() = _buttonsVisible

    private val _unlockCount =  database.getTodayUnlocksCountAfterTime(getCurrentDateInMilli())
    val unlockCount : LiveData<Int>
        get() {
            return  _unlockCount
        }
    private val _lastUnlock = database.getLastUnlockLiveData()
    val lastUnlock :LiveData<UnlockEvent>
    get() {
        return _lastUnlock
    }
    val lastunlockTime : LiveData<String> = Transformations.map( _lastUnlock , {user -> formatDateFromMillisecondsLong(user.eventTime)})
    var isAfter12Am = Calendar.getInstance().timeInMillis>=getToday12AmInMilli()

    private val _unlockEvents=if(isAfter12Am){ database.getAllUnlcoksFromTime(getToday12AmInMilli()) }
                            else{ database.getAllUnlcoksFromTime(getCurrentDateInMilli()) }

    val unlockEvents : LiveData<List<UnlockEvent>>
        get() {
            return  _unlockEvents
        }
     var testEvents : List<UnlockEvent> = listOf()
    var count =0

    //@SuppressLint("StaticFieldLeak")
    //private lateinit var mService :MyTestService

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Log.i("HomeViewModel","Bounded to service")
            val binder = service as MyTestService.LocalBinder
            //mService = binder.getService()
            _buttonsVisible.value=true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.i("HomeViewModel","Disconnected from service")
            _buttonsVisible.value =false
        }
    }

    init {
        viewModelScope.launch {
            testEvents=if(isAfter12Am){ database.getAllUnlcoksFromTimeNoLiveData(getToday12AmInMilli()) }
            else{ database.getAllUnlcoksFromTimeNoLiveData(getCurrentDateInMilli()) }
        }
        _buttonsVisible.value=false
        start()
    }
    fun start(){
        _buttonsVisible.value=true
        val _intent = Intent(context, MyTestService::class.java)
        _intent.action = START_MY_SERVICE
        context.startForegroundService(_intent)
        Intent(context,MyTestService::class.java).also { intent -> context.bindService(intent,connection,0) }
    }
    fun stop(){
        context.unbindService(connection)
        _buttonsVisible.value =false
        val _intent = Intent(context, MyTestService::class.java)
        _intent.action = STOP_MY_SERVICE
        context.stopService(_intent)
    }



}
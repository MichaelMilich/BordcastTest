package millich.michael.bordcasttest.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import millich.michael.bordcasttest.background.MyTestService
import millich.michael.bordcasttest.background.START_MY_SERVICE
import millich.michael.bordcasttest.background.STOP_MY_SERVICE
import millich.michael.bordcasttest.databse.UnlockDatabaseDAO

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var _intent : Intent
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _buttonsVisible= MutableLiveData<Boolean>()
    val buttonVisible : LiveData<Boolean>
    get() = _buttonsVisible

    init {
        _buttonsVisible.value=false
    }

    @SuppressLint("StaticFieldLeak")
    private lateinit var mService :MyTestService

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Log.i("HomeViewModel","Bounded to service")
            val binder = service as MyTestService.LocalBinder
            mService = binder.getService()
            _buttonsVisible.value=true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.i("HomeViewModel","Disconnected from service")
            _buttonsVisible.value =false
        }
    }

    fun start(){
        _buttonsVisible.value=true
        _intent = Intent(context, MyTestService::class.java)
        _intent.action = START_MY_SERVICE
        context.startForegroundService(_intent)
        Intent(context,MyTestService::class.java).also { intent -> context.bindService(intent,connection,0) }
    }
    fun stop(){
        context.unbindService(connection)
        _buttonsVisible.value =false
        _intent = Intent(context, MyTestService::class.java)
        _intent.action = STOP_MY_SERVICE
        context.stopService(_intent)
    }
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     */
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

}
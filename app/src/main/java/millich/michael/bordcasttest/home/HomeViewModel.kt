package millich.michael.bordcasttest.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
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
    fun start(){
        _buttonsVisible.value=true
        _intent = Intent(context, MyTestService::class.java)
        _intent.action = START_MY_SERVICE
        context.startForegroundService(_intent)
    }
    fun stop(){
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
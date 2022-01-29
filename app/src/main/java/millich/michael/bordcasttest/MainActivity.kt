package millich.michael.bordcasttest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import millich.michael.bordcasttest.background.CHANNEL_DESCRIPTION_1
import millich.michael.bordcasttest.background.CHANNEL_ID_1
import millich.michael.bordcasttest.background.CHANNEL_NAME_1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        //startFunction()
        super.onStart()
    }
    private fun createNotificationChannel()
    {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID_1,
            CHANNEL_NAME_1,
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = CHANNEL_DESCRIPTION_1
        mNotificationManager.createNotificationChannel(channel)
    }

}
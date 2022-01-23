package millich.michael.bordcasttest

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import millich.michael.bordcasttest.databse.UnlockDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel=ViewModelProvider(this).get(MyViewModel::class.java)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID_1,
            CHANNEL_NAME_1,
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = CHANNEL_DESCRIPTION_1
        mNotificationManager.createNotificationChannel(channel)

        val btnStartService = findViewById<Button>(R.id.buttonStartService)
        btnStartService.setOnClickListener {
            Snackbar.make(this,it,"Made start",Snackbar.LENGTH_SHORT).show()
            startFunction()
        }
        val btnStopService  = findViewById<Button>(R.id.buttonStopService)
        btnStopService.setOnClickListener {
            Snackbar.make(this,it,"Made stop",Snackbar.LENGTH_SHORT).show()
            stopFunction() }

    }

    private fun startFunction()
    {
        viewModel.start(applicationContext)
    }

    private fun stopFunction()
    {
        viewModel.stop(applicationContext)
    }

}
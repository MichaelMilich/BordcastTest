// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package millich.michael.bordcasttest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import millich.michael.bordcasttest.databse.UnlockDatabase
import millich.michael.bordcasttest.databse.UnlockEvent

object UnlockBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val database = UnlockDatabase.getInstance(context).unlockDatabaseDAO
        val unlockEvent = UnlockEvent()
        runBlocking {
            launch {
                database.Insert(unlockEvent)
                 val newUnlock = database.getLastUnlock()
                Log.i("Test", "Count = ${newUnlock!!.eventId} at Time = ${formatDateFromMillisecondsLong(newUnlock.eventTime)}")
               showNotification(context," ${newUnlock.eventId} Unlocks!" ,"Count = ${newUnlock.eventId} at Time = ${formatDateFromMillisecondsLong(newUnlock.eventTime)}")
            }
        }


    }
    private fun showNotification(context: Context, title: String, message: String) {
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val stopIntent = Intent(context, MyTestService::class.java)
        stopIntent.action=STOP_MY_SERVICE
        val pendingStopIntent = PendingIntent.getService(context,0,stopIntent,PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
            .setContentTitle(title) // title for notification
            .setContentText(message)// message for notification
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_launcher_background,context.resources.getString(R.string.stop_service),pendingStopIntent)
            .build()
        mNotificationManager.notify(ONGOING_NOTIFICATION_ID, notification)
    }
}
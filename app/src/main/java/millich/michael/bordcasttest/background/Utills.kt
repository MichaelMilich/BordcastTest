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
package millich.michael.bordcasttest.background

import android.util.Log
import androidx.lifecycle.LiveData
import millich.michael.bordcasttest.databse.UnlockEvent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val CHANNEL_ID_1 ="MISHA_CHANNEL_ID_1"

const val CHANNEL_NAME_1 ="MISHA_CHANNEL_NAME_1"

const val CHANNEL_DESCRIPTION_1 ="Channel for my application to show live data"

const val ONGOING_NOTIFICATION_ID=1234

const val STOP_MY_SERVICE="STOP_MY_SERVICE"

const val START_MY_SERVICE="START_MY_SERVICE"

fun formatDateFromMillisecondsLong( long: Long) :String
{
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
    simpleDateFormat.timeZone = TimeZone.getDefault()
    val date = Date(long)
    return simpleDateFormat.format(date)
}
fun getCurrentDateInMilli() : Long{
    var today = Calendar.getInstance()
    today.set(Calendar.MILLISECOND,0)
    today.set(Calendar.SECOND,0)
    today.set(Calendar.MINUTE,0)
    today.set(Calendar.HOUR_OF_DAY,0)
    Log.i("Test", "Today in Milliseconds is ${today.timeInMillis}")
    return today.timeInMillis
}

/*
fun getUnlockCountToday(unlocksToday: LiveData<List<UnlockEvent>>) : Int
{

}
*/
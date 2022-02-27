package millich.michael.bordcasttest.home

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.formatDateFromMillisecondsLong
import millich.michael.bordcasttest.databse.UnlockEvent

@BindingAdapter("unlockIdText")
fun TextView.setUnlockIdText(item : UnlockEvent){
    "unlock number ${item.eventId}".also { text=it }
}

@BindingAdapter("unlockTimeText")
fun TextView.setUnlockTimeText(item: UnlockEvent){
    text = formatDateFromMillisecondsLong(item.eventTime)
}

@BindingAdapter("unlockTimeTag")
fun ImageView.setTagImage(item: UnlockEvent){
    setImageResource(R.drawable.ic_dot)
}
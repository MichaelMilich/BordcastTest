package millich.michael.bordcasttest.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.calculateAngle
import millich.michael.bordcasttest.background.formatDateFromMillisecondsLong
import millich.michael.bordcasttest.databse.UnlockEvent
import java.time.Clock

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

@BindingAdapter("unlockTagView","radius")
fun ImageView.setTagView(item: UnlockEvent, parentWidth: Float){
    val angle1 = calculateAngle(item.eventTime)
    val angle =
        ((90 - angle1) * 0.017453).toFloat() // 0.017453 = 1 degree to radians
}
@BindingAdapter("ViewModel")
fun ClockView.setViewModel(viewModel: HomeViewModel){
    binding.viewModelClock=viewModel
    onBind()
}
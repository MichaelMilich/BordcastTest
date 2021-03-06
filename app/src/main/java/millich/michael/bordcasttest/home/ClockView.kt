package millich.michael.bordcasttest.home

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout

import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.calculateAngle
import millich.michael.bordcasttest.databinding.ClockViewBinding
import millich.michael.bordcasttest.databse.UnlockEvent
import kotlin.math.cos
import kotlin.math.sin

class ClockView : RelativeLayout {
    constructor(context: Context?) : super(context) {
        test1()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        test2()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        test3()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        test4()
    }
    val binding: ClockViewBinding =
        ClockViewBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var viewModel: HomeViewModel
    private var eventViewMap : MutableMap<Long,ImageView> = mutableMapOf()
    private fun test1() {
        Log.i("Test", "Function called from constuctor 1")
    }

    private fun test2() {
        Log.i("Test", "Function called from constuctor 2")
    }

    private fun test3() {
        Log.i("Test", "Function called from constuctor 3")
    }

    private fun test4() {
        Log.i("Test", "Function called from constuctor 4")
    }

    fun onBind() {
        viewModel = binding.viewModelClock!!
        if (viewModel.isAfter12Am.value!!)
            binding.analogClockView.setImageResource(R.drawable.ic_analog_clock_12_24)
        viewModel.isAfter12Am.observe(binding.lifecycleOwner!!,androidx.lifecycle.Observer{
            checkClock()
        })
    }
    fun checkClock(){
        if (viewModel.isAfter12Am.value!!)
            binding.analogClockView.setImageResource(R.drawable.ic_analog_clock_12_24)
        else
            binding.analogClockView.setImageResource(R.drawable.ic_analog_clock_0_12)
    }
    fun createTimeTags( eventList: List<UnlockEvent>,radius: Float) {
        Log.i("Test", "radius is $radius")
        for (event in eventList) {
            val key = event.eventId
            if (!eventViewMap.containsKey(key)) {
                val testImageView = ImageView(context)
                testImageView.setImageResource(R.drawable.ic_dot)
                val angle1 = calculateAngle(event.eventTime)
                val angle =
                    ((90 - angle1) * 0.017453).toFloat() // 0.017453 = 1 degree to radians
                val imageParameters =
                    LayoutParams(40, 40)
                imageParameters.addRule(CENTER_IN_PARENT, TRUE)
                testImageView.layoutParams = imageParameters
                testImageView.translationX = radius * cos(angle)
                testImageView.translationY = -radius * sin(angle)
                testImageView.rotation = angle1
                eventViewMap[key] = testImageView
                binding.relativeLayout.addView(testImageView)
            }
        }

    }
}
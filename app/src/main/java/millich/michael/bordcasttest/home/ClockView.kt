package millich.michael.bordcasttest.home

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.calculateAngle
import millich.michael.bordcasttest.databinding.ClockViewBinding
import millich.michael.bordcasttest.databinding.HomeFragmentBinding
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
        if (viewModel.isAfter12Am)
            binding.analogClockView.setImageResource(R.drawable.ic_analog_clock_12_24)
    }
    suspend fun createTimeTags( eventList: List<UnlockEvent>) {
        viewModel.count++
        Log.i("COUNT", "count is ${viewModel.count}")
        val radius = (binding.relativeLayoutTest.width / 2).toFloat()
        Log.i("width", "radius is $radius")
        val r: Float =
            radius + 0.5f

        for (event in eventList) {
            val key = event.eventId
            if (!eventViewMap.containsKey(key)) {
                val testImageView: ImageView = ImageView(context)
                testImageView.setImageResource(R.drawable.ic_dot)
                val angle1 = calculateAngle(event.eventTime)
                val angle =
                    ((90 - angle1) * 0.017453).toFloat() // 0.017453 = 1 degree to radians
                val imageParameters: RelativeLayout.LayoutParams =
                    RelativeLayout.LayoutParams(40, 40)
                imageParameters.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
                testImageView.layoutParams = imageParameters
                testImageView.translationX = r * cos(angle)
                testImageView.translationY = -r * sin(angle)
                testImageView.rotation = angle1
                eventViewMap.put(key,testImageView)
                binding.relativeLayoutTest.addView(testImageView)
            }
        }

    }
}
package millich.michael.bordcasttest.home

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.calculateAngle
import millich.michael.bordcasttest.background.getToday12AmInMilli
import millich.michael.bordcasttest.databinding.HomeFragmentBinding
import millich.michael.bordcasttest.databse.UnlockDatabase
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : HomeFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val databaseDAO = UnlockDatabase.getInstance(application).unlockDatabaseDAO
        val viewModelFactory = HomeViewModelFactory(application,databaseDAO)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        binding.viewModelTest=viewModel
        binding.buttonStartService.setOnClickListener{
            this.context?.let { it1 -> Snackbar.make(it1,it,"Made start",Snackbar.LENGTH_SHORT).show() }
            viewModel.start()
        }
        binding.buttonStopService.setOnClickListener{
            this.context?.let { it1 -> Snackbar.make(it1,it,"Made stop",Snackbar.LENGTH_SHORT).show() }
            viewModel.stop()
        }
        if(viewModel.isAfter12Am)
            binding.analogClockView.setImageResource(R.drawable.ic_analog_clock_12_24)
        var count =1
        for (event in binding.viewModelTest.unlockEvents.value!!)
        {
            Log.i("Test","count = $count and the eventId =${event.eventId}")
            count++
        }

        createTimeTags(binding,viewModel)

        binding.lifecycleOwner = this
        return binding.root
    }


    private fun createTimeTags(binding : HomeFragmentBinding, homeViewModel: HomeViewModel) {
        val scale = context?.resources?.displayMetrics?.density ?:0f
        val eventList = homeViewModel.unlockEvents.value
        val r : Float =  (311/2) *scale +0.5f // DEVELOPING STAGE - 311 = Diameter, have to find a better way to get the diameter

        if (eventList != null) {
            var count =1
            for(event in eventList){
                Log.i("Test","count = $count and the eventId =${event.eventId}")
                count++
                val testImageView: ImageView = ImageView(context)
                testImageView.setImageResource(R.drawable.ic_dot)
                val angle1 = calculateAngle(event.eventTime)
                val angle = ((90-angle1) *0.017453).toFloat() // 0.017453 = 1 degree to radians
                val imageParameters : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(40,40)
                imageParameters.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE)
                testImageView.layoutParams = imageParameters
                testImageView.translationX = r * cos(angle)
                testImageView.translationY = -r * sin(angle)
                testImageView.rotation =  angle1
                binding.relativeLayoutTest.addView(testImageView)
            }
        }
        /*val testImageView: ImageView = ImageView(context)
        testImageView.setImageResource(R.drawable.ic_dot)
        testImageView.id = binding.analogClockView.id +1



        val angle1 = calculateAngle(Calendar.getInstance().timeInMillis)
        val angle = ((90-angle1) *0.017453).toFloat() // 0.017453 = 1 degree to radians
        val imageParameters : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(40,40)
        imageParameters.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE)
        testImageView.layoutParams = imageParameters
        testImageView.translationX = r * cos(angle)
        testImageView.translationY = -r * sin(angle)
        testImageView.rotation =  angle1
        binding.relativeLayoutTest.addView(testImageView)*/

    }

}
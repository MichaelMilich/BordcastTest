package millich.michael.bordcasttest.home

import android.graphics.drawable.Drawable
import android.os.Build
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.calculateAngle
import millich.michael.bordcasttest.background.getToday12AmInMilli
import millich.michael.bordcasttest.databinding.HomeFragmentBinding
import millich.michael.bordcasttest.databse.UnlockDatabase
import millich.michael.bordcasttest.databse.UnlockEvent
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    private lateinit var viewModel: HomeViewModel
    private lateinit var binder:HomeFragmentBinding

    private  var radius:Float =(264 / 2).toFloat()

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
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
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

        val adapter = UnlockEventAdapter()
        binding.unlockList.adapter=adapter
        binder=binding


        viewModel.unlockEvents.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                if(it.isNotEmpty()) {
                    val firstId = it[it.size - 1].eventId - 1
                    for (event in it)
                        event.eventId -= firstId

                    createTimeTags(binder,it)
                }
                adapter.submitList(it)
            }
        })


        binding.lifecycleOwner = this
        return binding.root
    }


    private  fun createTimeTags(binding : HomeFragmentBinding, eventList: List<UnlockEvent>) {
        viewModel.count++
        Log.i("COUNT","count is ${viewModel.count}")
        if (viewModel.count<2) {
            viewLifecycleOwner.lifecycleScope.launch {
                radius = (binding.relativeLayoutTest.width / 2).toFloat()
                val r: Float =
                    radius + 0.5f

                for (event in eventList) {
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
                    binding.relativeLayoutTest.addView(testImageView)
                }
            }
        }
    }

}
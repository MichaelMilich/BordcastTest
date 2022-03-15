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
import millich.michael.bordcasttest.background.afterMeasured
import millich.michael.bordcasttest.background.calculateAngle
import millich.michael.bordcasttest.background.getToday12AmInMilli
import millich.michael.bordcasttest.databinding.HomeFragmentBinding
import millich.michael.bordcasttest.databse.UnlockDatabase
import millich.michael.bordcasttest.databse.UnlockEvent
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binder:HomeFragmentBinding
    private lateinit var clockView: ClockView

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

        val adapter = UnlockEventAdapter()
        binding.unlockList.adapter=adapter



        viewModel.unlockEvents.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                if(it.isNotEmpty()) {
                    val firstId = it[it.size - 1].eventId - 1
                    for (event in it)
                        event.eventId -= firstId

                    callClockViewTags(it)
                }
                adapter.submitList(it)
            }
        })

        binding.clockView.binding.lifecycleOwner=this
        binding.lifecycleOwner = this
        binder=binding
        clockView=binder.clockView
        return binding.root
    }

    private fun callClockViewTags(eventList: List<UnlockEvent>){
        viewLifecycleOwner.lifecycleScope.launch {
            clockView.afterMeasured {
                Log.i("Test","after Measured width of clock_view is =${clockView.binding.analogClockView.width}")
                clockView.createTimeTags(eventList,(clockView.binding.analogClockView.width/2).toFloat()+0.5f)
            }
        }
    }


}
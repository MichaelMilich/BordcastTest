package millich.michael.bordcasttest.home

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import millich.michael.bordcasttest.databinding.HomeFragmentBinding
import millich.michael.bordcasttest.databse.UnlockDatabase
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

        val testImageView: ImageView = ImageView(context)
        testImageView.setImageResource(R.drawable.ic_dot)
        testImageView.id = R.drawable.ic_dot+20

        val r : Float =  (binding.analogClockView.width).toFloat()
        val angle = 45f
        val imageParameters : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(40,40)
        imageParameters.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE)
        testImageView.viewTreeObserver.addOnGlobalLayoutListener {
            ViewTreeObserver.OnGlobalLayoutListener {
                
            }
        }
        testImageView.layoutParams = imageParameters
        testImageView.translationX = r * sin(angle)
        testImageView.translationY = -r * cos(angle)
        testImageView.rotation = angle
        binding.relativeLayoutTest.addView(testImageView)

        /*val constraintSet : ConstraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintLayout)
        constraintSet.connect(testImageView.id,ConstraintSet.START,binding.analogClockView.id,ConstraintSet.START)
        constraintSet.connect(testImageView.id,ConstraintSet.END,binding.analogClockView.id,ConstraintSet.END)
        constraintSet.connect(testImageView.id,ConstraintSet.TOP,binding.analogClockView.id,ConstraintSet.TOP)
        constraintSet.connect(testImageView.id,ConstraintSet.BOTTOM,binding.analogClockView.id,ConstraintSet.BOTTOM)
        constraintSet.applyTo(binding.constraintLayout)*/


        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //viewModel start the service automaticaly once the view was created
        super.onViewCreated(view, savedInstanceState)
    }

}
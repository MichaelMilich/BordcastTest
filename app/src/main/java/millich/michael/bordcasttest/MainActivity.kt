package millich.michael.bordcasttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import millich.michael.bordcasttest.databse.UnlockDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel=ViewModelProvider(this).get(MyViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        viewModel.start(this)
    }
}
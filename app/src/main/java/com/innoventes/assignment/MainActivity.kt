package com.innoventes.assignment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.innoventes.assignment.databinding.ActivityMainBinding
import com.innoventes.assignment.viewModel.UserDetailsViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Initialize ViewModel
        viewModel = UserDetailsViewModel()

        // Set the viewModel variable in the layout
        binding.viewModel = viewModel

        // Set lifecycle owner for LiveData to update properly
        binding.lifecycleOwner = this

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Observe changes in the enabled state of the Next button
        viewModel.isNextButtonEnabled.observe(this) { isEnabled ->
            // Update UI to reflect the enabled/disabled state of the Next button
            if (isEnabled) {
                binding.nextButton.setBackgroundResource(R.drawable.button_background)
                binding.nextButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {
                binding.nextButton.setBackgroundResource(R.drawable.button_grey_background)
                binding.nextButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
            binding.nextButton.isEnabled = isEnabled
        }


        binding.iHaveNoPanTextView.setOnClickListener(this)
        binding.tvDate.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
           R.id.nextButton -> {
               Toast.makeText(this, "Details submitted successfully", Toast.LENGTH_SHORT).show()
           }
            R.id.iHaveNoPanTextView -> {
                finish()
            }
            R.id.tvDate -> {
                viewModel.dateSelected(this)
            }
            R.id.tvMonth -> {
                viewModel.dateSelected(this)
            }
            R.id.tvYear -> {
                viewModel.dateSelected(this)
            }
        }
    }

}
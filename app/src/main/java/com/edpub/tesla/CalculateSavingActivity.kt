package com.edpub.tesla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.edpub.tesla.databinding.ActivityCalculateSavingBinding

class CalculateSavingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculateSavingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalculateSavingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.tbCalculateSavingScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()

    }

    fun initView(){
        ArrayAdapter.createFromResource(
            this,
            R.array.months,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.sMonth.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.ac_tonnage,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.sTonnage.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.states,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.sState.adapter = adapter
        }



        binding.cvCalculate.setOnClickListener {
            val month = binding.sMonth.selectedItem
            val ton = binding.sTonnage.selectedItem
            val state = binding.sState.selectedItem

            Log.i("CalcSav", month.toString()+ " " + ton.toString() + " " + state.toString())
        }

    }
}
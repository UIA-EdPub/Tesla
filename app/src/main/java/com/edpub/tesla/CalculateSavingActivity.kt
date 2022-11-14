package com.edpub.tesla

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.edpub.tesla.databinding.ActivityCalculateSavingBinding


class CalculateSavingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculateSavingBinding

    private var lastLatitude = ""
    private var lastLongitude = ""
    private var roofImageString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalculateSavingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.tbCalculateSavingScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        roofImageString = intent.getStringExtra("mapImagePath").toString()
        lastLatitude = intent.getStringExtra("lastLatitude").toString()
        lastLongitude = intent.getStringExtra("lastLongitude").toString()

        initView()

    }

    private fun initView(){
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

            UtilityFunctions.getStringData(this, lastLatitude, lastLongitude,object:ResponseCallback{
                override fun onSuccess(response: String) {
                    val valueMap = UtilityFunctions.converter(response)
                    val propertiesMap = valueMap["properties"] as Map<String, Any?>
                    val parameterMap = propertiesMap["parameter"] as Map<String, Any?>
                    val temperatureMap = parameterMap["T2M"] as Map<String, Any?>
                    val energyMap = parameterMap["ALLSKY_SFC_PAR_TOT"] as Map<String, Any?>

                    Log.i("extracttion", temperatureMap.size.toString())
                    Log.i("extracttion", energyMap.size.toString())

                }

                override fun onError(error: String) {
                    Log.i("tag", error)
                }

            })
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
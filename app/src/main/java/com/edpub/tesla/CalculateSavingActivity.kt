package com.edpub.tesla

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
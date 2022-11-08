package com.edpub.tesla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edpub.tesla.databinding.ActivityCalculateSavingBinding

class CalculateSavingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculateSavingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalculateSavingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.tbCalculateSavingScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}
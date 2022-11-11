package com.edpub.tesla

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.edpub.tesla.databinding.ActivityEditMapAreaBinding


class EditMapAreaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditMapAreaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditMapAreaBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val roofImageString = intent.getStringExtra("mapImagePath")
        Log.i("SexyPath", roofImageString.toString())
        val roofImageUri = Uri.parse(roofImageString)
        binding.ivRoofImage.setImageURI(roofImageUri)

        initView()
    }
    private fun initView(){
        binding.cvConfirm.setOnClickListener {
            val intent = Intent(this, CalculateSavingActivity::class.java)
            startActivity(intent)

        }

        binding.cvRetake.setOnClickListener {
            super.onBackPressed()
        }
    }
}
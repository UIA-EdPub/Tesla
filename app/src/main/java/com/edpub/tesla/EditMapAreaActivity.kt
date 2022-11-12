package com.edpub.tesla

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.edpub.tesla.databinding.ActivityEditMapAreaBinding


class EditMapAreaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditMapAreaBinding

    private lateinit var roofImageUri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditMapAreaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.tbEditMap)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val roofImageString = intent.getStringExtra("mapImagePath")
        Log.i("SexyPath", roofImageString.toString())
        roofImageUri = Uri.parse(roofImageString)//gives null pointer exception
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
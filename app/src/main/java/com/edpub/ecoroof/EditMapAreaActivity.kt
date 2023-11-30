package com.edpub.ecoroof

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.edpub.ecoroof.databinding.ActivityEditMapAreaBinding

class EditMapAreaActivity : AppCompatActivity() {

    private val TAG = "EditMapAreaActivityTag"

    private lateinit var binding: ActivityEditMapAreaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditMapAreaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val uri = Uri.parse(intent.getStringExtra("uri"))
        val mapArea = intent.getDoubleExtra("mapArea", 0.0)
        Log.i("mapAreaTag", "Edit: " + mapArea.toString())
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        binding.cvConfirmImage.setOnClickListener {
            val croppedImage = binding.cropImageView.getCroppedImage()
            val croppedImageUri = Utils.getUriFromBitmap(croppedImage!!, contentResolver)

            Log.i(TAG, croppedImageUri.toString())

            val intent = Intent(this, CalculateSavingsActivity::class.java)
            intent.putExtra("uri", croppedImageUri.toString())
            intent.putExtra("mapArea", mapArea)
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
            startActivity(intent)

        }
        binding.cropImageView.setImageUriAsync(uri)
    }
}
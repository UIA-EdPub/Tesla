package com.edpub.tesla

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
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
    }
}
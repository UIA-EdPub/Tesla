package com.edpub.tesla

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.ByteArrayOutputStream


object UtilityFunctions {

    //all temps in degree celcius
    const val min_temp = 18
    const val max_temp = 26
    //if avg temp of day lies in this range: no AC required

    fun savingInOneDay(r: Float, e: Float) {

    }

    fun getUriFromBitmap(bitmap: Bitmap, contentResolver: ContentResolver): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
            ?: return null
        return Uri.parse(path)
    }


}
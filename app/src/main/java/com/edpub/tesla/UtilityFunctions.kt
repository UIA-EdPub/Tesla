package com.edpub.tesla

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
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
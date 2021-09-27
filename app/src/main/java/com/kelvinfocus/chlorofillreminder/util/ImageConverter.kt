package com.kelvinfocus.chlorofillreminder.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

object ImageConverter {
    fun ImageView.to64ByteString(): String {
        val bitmap = this.drawable.toBitmap()
        val imageArray = bitmap.toByteArray()
        return Base64.encodeToString(imageArray, Base64.DEFAULT)
    }

    fun Bitmap.toByteArray(
        compressionFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        compressionQuality: Int = 100
    ): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(compressionFormat, compressionQuality, stream)
        return stream.toByteArray()
    }

    fun bitmapToBase64String(bitmap: Bitmap, encodeFlag: Int = Base64.DEFAULT): String? {
        val byteArray = bitmap.toByteArray()
        return Base64.encodeToString(byteArray, encodeFlag)
    }

    fun base64StringToBitmap(imageString: String, decodeFlag: Int = Base64.DEFAULT): Bitmap? {
        val imageArray = Base64.decode(imageString, decodeFlag)
        return BitmapFactory.decodeByteArray(imageArray, 0, imageArray.size)
    }
}
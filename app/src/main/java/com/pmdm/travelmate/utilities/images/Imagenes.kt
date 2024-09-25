package com.pmdm.travelmate.utilities.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

class Imagenes {
    companion object {
        fun base64ToAndroidBitmap(base64ImageString: String): Bitmap {
            val decodedString: ByteArray? = Base64.decode(base64ImageString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString!!.size)
        }

        fun base64ToBitmap(base64ImageString: String) =
            base64ToAndroidBitmap(base64ImageString).asImageBitmap()


        fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
            return Bitmap.createScaledBitmap(bitmap, width, height, false)
        }

        @RequiresApi(Build.VERSION_CODES.R)
        fun androidBitmapToBase64(
            bitmap: Bitmap,
            width: Int = 1080,
            height: Int = 1920
        ): String {
            val resizedBitmap = resizeBitmap(bitmap, width, height)
            return ByteArrayOutputStream().apply {
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, this)
            }.use { stream ->
                Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
            }
        }


        @RequiresApi(Build.VERSION_CODES.R)
        fun bitmapToBase64(bitmap: ImageBitmap): String =
            androidBitmapToBase64(bitmap.asAndroidBitmap())

        fun base64ToBitmap(biteArray: ByteArray?): ImageBitmap? {
            return if (biteArray != null)
                BitmapFactory.decodeByteArray(biteArray, 0, biteArray.size).asImageBitmap()
            else null;
        }

        fun bitmapToBase64(bitmap: ImageBitmap?): ByteArray? {
            if (bitmap != null) {
                val stream = ByteArrayOutputStream()
                bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 70, stream)
                return stream.toByteArray()
            }
            return null
        }

        fun androidBitmapFromRerouceId(recurso: Int, context: Context): Bitmap =
            BitmapFactory.decodeResource(context.resources, recurso)

        fun androidBitmapFromURI(uri: Uri, context: Context): Bitmap {
            val contextResolver = context.contentResolver
            val source = ImageDecoder.createSource(contextResolver, uri)
            return ImageDecoder.decodeBitmap(source)
        }

        fun bitmapFromURI(uri: Uri, context: Context) =
            androidBitmapFromURI(uri, context).asImageBitmap()

        fun blobToBase64(value: ByteArray?): String? =
            value?.let {
                Base64.encodeToString(value, Base64.DEFAULT)
            }

        fun base64ToBlob(value: String?): ByteArray? =
            value?.let {
                Base64.decode(value, Base64.DEFAULT)
            }
    }
}
package com.pmdm.travelmate.utilities.device

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.pmdm.travelmate.utilities.images.Imagenes
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


// Uso:
//      slectorDeImagenes.launch("image/*")
@Composable
fun ResgitroSelectorDeImagenesConGetContent(
    onFotoCambiada: (ImageBitmap) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                onFotoCambiada(Imagenes.bitmapFromURI(uri, context))
            }
        }
    )
}

// Para hacer fotos con TakePicture, hay que añadir en el manifest:
// <uses-feature
//      android:name="android.hardware.camera.any"
//      android:required="true" />
// <uses-permission android:name="android.permission.CAMERA" />
//
// En el manifest, dentro de <application>:
// <provider
//     android:name="androidx.core.content.FileProvider"
//     android:authorities="${applicationId}.provider"
//     android:exported="false"
//     android:grantUriPermissions="true">
//     <meta-data
//         android:name="android.support.FILE_PROVIDER_PATHS"
//         android:resource="@xml/path_provider" />
// </provider>
//
// En res/xml/path_provider.xml:
// <?xml version="1.0" encoding="utf-8"?>
// <paths>
//     <external-cache-path
//         name="my_images"
//         path="/"/>
// </paths>
// Uso:
//      hacerFoto.launch(android.Manifest.permission.CAMERA)
@Composable
fun ResgitroHacerFotoConTakePicture(
    onFotoCambiada: (ImageBitmap) -> Unit
): ManagedActivityResultLauncher<String, Boolean> {

    val context = LocalContext.current
    val ficheroTemporal = File.createTempFile(
        "JPEG_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}_",
        ".jpg",
        context.externalCacheDir
    )
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        ficheroTemporal
    )
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                onFotoCambiada(Imagenes.bitmapFromURI(uri, context))
            }
        }
    return rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { success ->
        if (success) {
            cameraLauncher.launch(uri)
        }
    }
}

// Para hacer fotos con el intent de la cámara, hay que añadir en el manifest:
// <uses-feature
//      android:name="android.hardware.camera.any"
//      android:required="true" />
// <uses-permission android:name="android.permission.CAMERA" />
// Uso:
//      hacerFoto.launch(android.Manifest.permission.CAMERA)
@Composable
fun ResgitroHacerFotoConIntent(
    onFotoCambiada: (ImageBitmap) -> Unit
): ManagedActivityResultLauncher<String, Boolean> {
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val androidBitmap = result.data?.extras?.get("data") as Bitmap
                onFotoCambiada(androidBitmap!!.asImageBitmap())
            }
        }

    return rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { success ->
        if (success) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
            cameraLauncher.launch(cameraIntent)
        }
    }
}


// Para hacer llamadas telefónicas, hay que añadir en el manifest:
// <uses-feature
//      android:name="android.hardware.telephony"
//      android:required="true" />
// <uses-permission android:name="android.permission.CALL_PHONE"/>
// Uso:
//      telefono.launch(android.Manifest.permission.CALL_PHONE)

@Composable
fun ResgitroLlamarPorTelefonoIntent(
    telefono: String
): ManagedActivityResultLauncher<String, Boolean> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { success ->
        if (success) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$telefono")
            context.startActivity(callIntent)
        }
    }
}

fun EnviarMailIntent(
    context: Context,
    correo: String
) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, correo);
    context.startActivity(intent)
}
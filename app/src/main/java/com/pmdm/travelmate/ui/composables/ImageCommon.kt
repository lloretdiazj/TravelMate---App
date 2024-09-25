package com.pmdm.travelmate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pmdm.travelmate.R
import com.pmdm.travelmate.utilities.device.ResgitroSelectorDeImagenesConGetContent
import com.pmdm.travelmate.utilities.images.Imagenes


@Composable
fun CircularImageFromResource(
    idImageResource: Int, contentDescription: String, size: Dp = Dp(130f)
) {

    Image(
        painter = painterResource(idImageResource),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop, //Si la imagen no es cuadrada
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun CircularImageFromResourceClickable(
    idImageResource: String?,
    contentDescription: String, size: Dp = Dp(130f),
    onFotoCambiada: (ImageBitmap) -> Unit = {}
) {

    val selectorDeImagenes = ResgitroSelectorDeImagenesConGetContent(
        onFotoCambiada
    )
    Image(
        painter =
        if (idImageResource == null || idImageResource == "")
            painterResource(R.drawable.usuario)
        else
            BitmapPainter(Imagenes.base64ToBitmap(idImageResource)),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop, //Si la imagen no es cuadrada
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color = Color.White)
            .clickable {
                selectorDeImagenes.launch("image/*")
            }
    )
}

@Composable
fun CircularImageFromResourceClickableNuevoViaje(
    idImageResource: String?,
    contentDescription: String, size: Dp = Dp(130f),
    onFotoCambiada: (ImageBitmap) -> Unit = {}
) {

    val selectorDeImagenes = ResgitroSelectorDeImagenesConGetContent(
        onFotoCambiada
    )
    Image(
        painter =
        if (idImageResource == null || idImageResource == "")
            painterResource(R.drawable.viajedefecto)
        else
            BitmapPainter(Imagenes.base64ToBitmap(idImageResource)),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop, //Si la imagen no es cuadrada
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color = Color.White)
            .clickable {
                selectorDeImagenes.launch("image/*")
            }
    )
}

@Composable
fun ImageFromResource(
    painter: Painter,
    contentDescription: String,
    size: Dp = Dp(130f),
) {

    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop, //Si la imagen no es cuadrada
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)

    )
}

@Composable
fun CircularImageFromResource(
    imageVector: ImageVector, contentDescription: String, size: Dp = Dp(130f)
) {

    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop, //Si la imagen no es cuadrada
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color = Color.White)
    )
}

@Composable
fun CircularImageFromPainterResource(
    imagen: String?, contentDescription: String, size: Dp = Dp(130f)
) {

    Image(
        painter =
        if (imagen == null || imagen == "")
            painterResource(R.drawable.usuario)
        else
            BitmapPainter(Imagenes.base64ToBitmap(imagen)),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop, //Si la imagen no es cuadrada
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color = Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun ImageCommonTest() {
    CircularImageFromResource(idImageResource = R.drawable.login, contentDescription = "Logearse")
}

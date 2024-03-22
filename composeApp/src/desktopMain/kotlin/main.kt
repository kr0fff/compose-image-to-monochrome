import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xml.sax.InputSource
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.Base64
import javax.imageio.ImageIO

fun main() =  //singleApplication
    application() {
        val state = rememberWindowState()

        Window(
            onCloseRequest = ::exitApplication, title = "KotlinProject"
        ) {

            var file: File? by remember { mutableStateOf(null) }
            var isFileChooserOpen by remember { mutableStateOf(false) }
            if (isFileChooserOpen) {
                fileDialog(
                    onCloseRequest = {
                        isFileChooserOpen = false
                        if (it != null) {
                            val resultFile = File("monochrome.png")
                            val imagePng = toMonochrome(ImageIO.read(File(it)))
                            ImageIO.write(imagePng, "png", resultFile)
                            file = resultFile

                            val base64 = imageToBase64(imagePng)
                            val selector = StringSelection(base64)
                            Toolkit.getDefaultToolkit().systemClipboard.setContents(selector, selector)
                            println("Base64: $base64")
                        }
                    }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                MaterialTheme {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            file = null
                            isFileChooserOpen = true
                        }
                    ) {
                        Text("Open")
                        println("File: $file")
                    }
                }
                loadImage(file)
            }
        }
    }

@Composable
fun loadImage(file: File?) {
    if (file != null) {
        println("Image: $file")
        AsyncImage(
            load = { loadImageBitmap(file) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "Sample",
            modifier = Modifier.fillMaxWidth()
        )
        Text("File transformed to monochrome and copied to clipboard.")
    } else {
        Text("Please select the image file to load.")
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        println("Not loaded yet...")
    }
}

/* Loading from file with java.io API */

fun loadImageBitmap(file: File): ImageBitmap =
    file.inputStream().buffered().use(::loadImageBitmap)

fun loadSvgPainter(file: File, density: Density): Painter =
    file.inputStream().buffered().use { loadSvgPainter(it, density) }

fun loadXmlImageVector(file: File, density: Density): ImageVector =
    file.inputStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

/* Loading from network with java.net API */

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)

fun loadSvgPainter(url: String, density: Density): Painter =
    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }

fun loadXmlImageVector(url: String, density: Density): ImageVector =
    URL(url).openStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

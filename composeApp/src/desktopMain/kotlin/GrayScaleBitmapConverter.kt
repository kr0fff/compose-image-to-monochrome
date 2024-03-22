import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.Base64
import javax.imageio.ImageIO

fun imageToBase64(buffer: BufferedImage): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    ImageIO.write(buffer, "png", byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.getEncoder().encodeToString(byteArray)
}

fun toMonochrome(img: BufferedImage): BufferedImage {
    val grayImage = BufferedImage(img.width, img.height, BufferedImage.TYPE_BYTE_BINARY)

    for (y in 0 until img.height) {
        for (x in 0 until img.width) {
            val rgb = img.getRGB(x, y)
            val r = (rgb shr 16) and 0xFF
            val g = (rgb shr 8) and 0xFF
            val b = rgb and 0xFF
            val gray = (r + g + b) / 3

            val newRgb = (0xFF shl 24) or (gray shl 16) or (gray shl 8) or gray
            grayImage.setRGB(x, y, newRgb)
        }
    }
    return grayImage
}


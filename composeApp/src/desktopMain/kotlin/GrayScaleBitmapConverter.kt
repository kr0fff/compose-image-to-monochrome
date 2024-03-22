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

/*fun toMonochrome(file: File): File {
    var resFile = File("monochrome_sample.png")
    val master = ImageIO.read(file)
    val blackWhite = BufferedImage(master.width, master.height, BufferedImage.TYPE_BYTE_BINARY)
    val g2d = (blackWhite.createGraphics() as Graphics2D).apply { drawImage(master, 0, 0, null) }
    g2d.drawImage(master, 0, 0, null)
    ImageIO.write(master, "png", resFile)
//    var pixels = blackWhite.getRGB(, 0, blackWhite.width, blackWhite.height, 0, blackWhite.width)
    return resFile
}*/

/*
fun bitmapMonochrome(file: File) {
    val master = ImageIO.read(file)
    var bufferedImage = BufferedImage(master.width, master.height, BufferedImage.TYPE_BYTE_BINARY)
    bufferedImage = ImageIO.read(file)
}
*/

/*fun get2DPixelArraySlow(sampleImage: BufferedImage): IntArray {
    val width = sampleImage.width
    val height = sampleImage.height
    var col = IntArray(sampleImage.height)
    var row = IntArray(sampleImage.width)
    val result = IntArray(width * height) // More efficient single-dimensional array

    var index = 0
    for (row in 0 until height) {
        for (col in 0 until width) {
            val pixel = sampleImage.getRGB(col, row)

            val alpha = pixel and 0xFF000000.toInt() shr 24
            val red = pixel and 0x00FF0000 shr 16
            val green = pixel and 0x0000FF00 shr 8
            val blue = pixel and 0x000000FF

            var gray = (alpha + 0.2989 * red + 0.5870 * green + 0.1140 * blue).toInt()
            gray = if (gray > 128) 0 else 255
            result[index] = gray
            index++
        }
    }

    return result
}*/
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

fun get2DPixelByteArrayMonochrome(sampleImage: BufferedImage): ByteArray {
    val width = sampleImage.width
    val height = sampleImage.height
    val byteArraySize = width * height * 4 // 4 bytes per ARGB value

    val byteArray = ByteArray(byteArraySize)
    var index = 0

    for (row in 0 until height) {
        for (col in 0 until width) {
            val pixel = sampleImage.getRGB(col, row)

            // Extract ARGB components (no conversion needed for byte array)
            val alpha = pixel shr 24 and 0xFF
            val red = pixel shr 16 and 0xFF
            val green = pixel shr 8 and 0xFF
            val blue = pixel and 0xFF

            // Store each component directly in the byte array
            byteArray[index] = alpha.toByte()
            byteArray[index + 1] = red.toByte()
            byteArray[index + 2] = green.toByte()
            byteArray[index + 3] = blue.toByte()

            index += 4
        }
    }

    return byteArray
}
/*fun fileToMonochrome(bufferedImage: BufferedImage): File{
    val byteArrayOutputStream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "png", byteArrayOutputStream)

    val byteArray = byteArrayOutputStream.toByteArray()



    val inStream = ByteArrayInputStream(byteArray)
    val outputFile = File("monochrome_test2.png")
    val newImage = ImageIO.read(inStream)

    ImageIO.write(newImage, "png", outputFile)
    return outputFile
}*/

/*
fun fileToMonochrome(bufferedImage: BufferedImage): File{
    val byteArrayOutputStream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "png", byteArrayOutputStream)

    val byteArray = byteArrayOutputStream.toByteArray()
    val outputByteArray = ByteArray(byteArray.size)
    for (i in byteArray.indices) {
        val pixel = byteArray[i].toInt() and 0xFF
        val grayValue = (pixel * 0.299 + pixel * 0.587 + pixel * 0.114).toInt()
        outputByteArray[i] = grayValue.toByte()
    }

    val inStream = ByteArrayInputStream(outputByteArray)
    val outputFile = File("monochrome_test3.png")
    val newImage = ImageIO.read(inStream)

    ImageIO.write(newImage, "png", outputFile)
    return outputFile
}
*/

fun get2DGrayscaleArray(sampleImage: BufferedImage): Array<IntArray> {
    val width = sampleImage.width
    val height = sampleImage.height
    val grayscaleArray = Array(height) { IntArray(width) } // 2D array for grayscale values

    for (row in 0 until height) {
        for (col in 0 until width) {
            val pixel = sampleImage.getRGB(col, row)

            // Calculate grayscale value (same logic as before)
            val alpha = pixel and 0xFF000000.toInt() shr 24
            val red = pixel and 0x00FF0000 shr 16
            val green = pixel and 0x0000FF00 shr 8
            val blue = pixel and 0x000000FF
            val gray = (alpha + 0.2989 * red + 0.5870 * green + 0.1140 * blue).toInt()
            grayscaleArray[row][col] = if (gray > 128) 0 else 255
        }
    }

    return grayscaleArray
}

fun imageFromByteArray(byteArray: ByteArray): File {
    val inStream = ByteArrayInputStream(byteArray)
    val outputFile = File("monochrome_test.png")
//    val decoded = Base64.getDecoder().decode(byteArray)

    try {

        val newImage = ImageIO.read(inStream)
        ImageIO.write(newImage, "png", outputFile)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return outputFile
}

fun createImageFromPixelArray(pixelArray: IntArray, width: Int, height: Int): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val raster = image.raster

    raster.setDataElements(0, 0, width, height, pixelArray)
    return image
}



import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.skia.Bitmap
import java.awt.FileDialog
import java.awt.Frame
import java.awt.Window
import java.io.File
import java.io.FilenameFilter
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun loaderApplication() = application {

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FileDialog(
    parent: Window? = null,
    onCloseRequest: (result: String?) -> Unit
) {
    val fileChooser = JFileChooser()
    fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
    fileChooser.currentDirectory = File("C:/Downloads") // Set initial directory

    fileChooser.fileFilter = FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp")
    fileChooser.isAcceptAllFileFilterUsed = false


    if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
        onCloseRequest(fileChooser.selectedFile.path)
    } else {
        onCloseRequest(null)
    }
}
/*
fun toMonochrome(bitmap: Bitmap): Bitmap {
    val matrix = ColorMatrix()
    matrix.setSaturation(0f)

    val paint = Paint()
    paint.colorFilter = ColorMatrixColorFilter(matrix)

    val canvas = Canvas(bitmap)
    canvas.drawBitmap(bitmap, 0f, 0f, paint)

    return bitmap
}
*/

/*
@Composable
fun FileDialog(
    parent: Frame? = null,
    onCloseRequest: (result: String?) -> Unit,
    customFilter: FilenameFilter
) = AwtWindow(
    create = {
         object : FileDialog(parent, "Choose a file", LOAD) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                super.setDirectory("D://Games")
                super.setFilenameFilter(customFilter)
                if (value) {
                    onCloseRequest(file)
                }
            }
        }
    },
    dispose = FileDialog::dispose
)*/

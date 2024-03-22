import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Window
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun loaderApplication() = application {

}

@OptIn(ExperimentalComposeUiApi::class)
fun fileDialog(
    parent: Window? = null,
    onCloseRequest: (result: String?) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
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
}
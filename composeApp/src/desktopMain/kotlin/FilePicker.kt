/*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key.Companion.Window
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

@Composable
fun filePicker() {
    val selectedFile = remember { mutableStateOf<File?>(null) }

    Window(onCloseRequest = ::exitApplication, title = "KotlinProject") {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                val file = FileDialog.openFile()
                if (file != null) {
                    selectedFile.value = file
                }
            }) {
                Text("Выбрать файл")
            }

            if (selectedFile.value != null) {
                Text("Выбранный файл: ${selectedFile.value!!.name}")
            }
        }
    }
}*/

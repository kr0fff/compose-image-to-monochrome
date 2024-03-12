import javax.swing.filechooser.FileFilter
import javax.swing.filechooser.FileNameExtensionFilter

data class AcceptableFileFilters(
    val fileTypes: List<FileFilter> = listOf<FileFilter>(
        FileNameExtensionFilter("PNG", "png"),
        FileNameExtensionFilter("JPG", "jpg"),
        FileNameExtensionFilter("JPEG", "jpeg")
    )
)
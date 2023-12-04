import java.io.File

class Utils {
    fun getFile(fileName: String): File {
        return File("src/main/resources/"+fileName)
    }

}
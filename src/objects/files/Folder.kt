package objects.files

import objects.File
import objects.GameState
import java.util.*
import kotlin.collections.ArrayList

class Folder(fileName: String, var content: ArrayList<File>) : File(fileName) {
    var parentFolder: Folder? = null

    //Methods
    fun setParents(parentFolder: Folder?) {
        this.parentFolder = parentFolder

        content.forEach {if (it is Folder) it.setParents(this)}
    }

    fun getFile(fileName: String): File? {
        return content.find { it.fileName == fileName }
    }

    fun findFolder(gameState: GameState, filePath: String) : Pair<Folder?, String> {
        val tokenizer = StringTokenizer(filePath, "/")

        var currentFolder = this
        var token: String

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken()

            currentFolder = when (token) {
                ".." -> currentFolder.parentFolder ?: currentFolder
                else -> (currentFolder.getFile(token) ?: return Pair(null, gameState.error.objectNotFound(token))) as? Folder ?: return Pair(null, gameState.error.isNot(token, "a folder"))
            }
        }

        return Pair(currentFolder, "")
    }
}
package objects.files

import objects.File
import kotlin.collections.ArrayList

class Folder(fileName: String, var content: ArrayList<File>) : File(fileName) {
    var parentFolder: Folder? = null

    //Methods
    fun setParents(parentFolder: Folder?) {
        this.parentFolder = parentFolder

        content.forEach {if (it is Folder) it.setParents(this)}
    }

    fun findFile(fileName: String): File? {
        return content.find { it.fileName == fileName }
    }
}
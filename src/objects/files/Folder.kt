package objects.files

import objects.File

class Folder(fileName: String, var content: Array<File?>) : File(fileName) {
    private var parentFolder: Folder? = null
    //Methods
    fun setParents(parentFolder: Folder?) {
        this.parentFolder = parentFolder
        for (file in content) {
            if (file is Folder) {
                file.setParents(this)
            }
        }
    }
}

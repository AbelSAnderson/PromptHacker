package objects.files

import objects.File

class Folder(fileName: String, var content: ArrayList<File>) : File(fileName) {
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

    fun findFile(fileName: String): File? {
        for (file in this.content) {
            if(file.fileName == fileName) {
                if(file !is Folder) {
                }
                return file
            }
        }

        return null
    }
}

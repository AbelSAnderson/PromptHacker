package objects.computerSystems

import objects.files.Folder

class FileSystem(files: Folder) {
    var files: Folder
    var currentFolder: Folder = files

    init {
        this.files = this.currentFolder
    }
}
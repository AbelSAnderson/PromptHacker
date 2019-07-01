package objects.files

import objects.File

class TextFile(fileName: String, var content: String) : File(fileName)

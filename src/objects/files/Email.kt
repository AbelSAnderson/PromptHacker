package objects.files

import objects.File

class Email(fileName: String, var from: String, var to: String, var sent: String, var subject: String, var content: String) : File(fileName)

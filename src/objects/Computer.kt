package objects

import objects.files.Email
import objects.files.Folder

class Computer(var compName: String, var security: SecuritySys, var ipAddress: String, var connectedCompNames: Array<String>, var connectedComputers: MutableList<Computer>, val rootFolder: Folder, var currentFolder: Folder, var emails: Array<Email>)
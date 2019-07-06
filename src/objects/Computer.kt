package objects

import objects.files.Folder

class Computer(var compName: String, var security: SecuritySys, var ipAddress: String, var connectedComputers: Array<String?>, var currentFolder: Folder)
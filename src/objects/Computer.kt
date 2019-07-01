package objects

import objects.computerSystems.FileSystem

class Computer(var compName: String, var security: String, var ipAddress: String, var connectedComputers: Array<String?>, var files: FileSystem)
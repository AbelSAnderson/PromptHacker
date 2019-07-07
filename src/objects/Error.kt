package objects

//Empty Constructor
class Error {

    //Methods
    fun invalidUse(commandName: String, subCommand: String): String {
        return "'$commandName $subCommand' is not a valid use of '$commandName'."
    }

    fun wrongUse(oldCommand: String, newCommand: String): String {
        return "Invalid use of '$oldCommand', try using '$newCommand' instead."
    }

    fun objectNotFound(objectName: String, reason: String = ""): String {
        return "'$objectName' was not found.$reason"
    }

    fun commandNotFound(userCommand: String): String {
        return "$userCommand not found.\nType 'help' for available commands."
    }

    fun notAFolder(file: String): String {
        return "$file is not a folder."
    }

    fun notLoggedIn(): String {
        return "Log into computer to access that command."
    }

    fun noComputers(): String {
        return "No computers found."
    }
}
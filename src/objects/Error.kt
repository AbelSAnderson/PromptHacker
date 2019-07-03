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

    fun invalidFile(file: String, reason: String = ""): String {
        return "'$file' was not found.$reason"
    }

    fun unknownCommand(userCommand: String): String {
        return "$userCommand not found.\nType 'help' for available commands."
    }

    fun notAFolder(file: String): String {
        return "$file is not a folder."
    }
}
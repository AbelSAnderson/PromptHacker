package objects

import java.util.*

//Empty Constructor
class Error {

    val defaultMessage = "Type 'help' for available commands."

    //Methods
    fun invalidUse(commandName: String, subCommand: String = ""): String {
        var message = if(subCommand.isEmpty()) "'$commandName'"
        else "'$commandName $subCommand'"

        message += " is not a valid use of '$commandName'."

        return message
    }

    fun invalidUse(commandName: String, subCommands: StringTokenizer): String {

        var temp = ""
        while(subCommands.hasMoreTokens()) temp += " " + subCommands.nextToken()

        return "'$commandName ${temp.trim()}' is not a valid use of '$commandName'."
    }

    fun objectNotFound(objectName: String, reason: String = "", quotes: Boolean = true): String {
        var message = if(quotes) "'$objectName'"
        else objectName

        message += " not found."

        if(reason.isNotEmpty()) message += "\n$reason"

        return message
    }

    fun noObjectsFound(objectName: String): String {
        return "No $objectName found."
    }

    fun isNot(name: String, message: String): String {
        return "'$name' is not $message."
    }

    fun loggedIn(loggedIn: Boolean, computer: String = "this computer"): String {
        return if(loggedIn) "You are already logged in."
        else "Log into $computer to use that command."
    }
}
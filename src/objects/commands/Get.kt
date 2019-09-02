package objects.commands

import objects.Command
import objects.Error
import objects.GameState

class Get : Command("get", "get ip/name/pass: View a piece of information about your current computer", false) {

    override fun execute(gameState: GameState, userCommand: String): String {

        return when (userCommand.toLowerCase()) {
            "name" -> messageCreator("Name", gameState.currentComputer.compName)
            "ip" -> messageCreator("Ip Address", gameState.currentComputer.ipAddress)
            "pass" -> messageCreator("Password", gameState.currentComputer.security.password)
            else -> gameState.error.objectNotFound(userCommand)
        }
    }

    private fun messageCreator(message: String, variable: String): String {
        return if (variable.isEmpty()) Error().objectNotFound(message, quotes = false)
        else "Computer $message: $variable"
    }
}
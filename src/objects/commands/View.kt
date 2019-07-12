package objects.commands

import objects.Command
import objects.Error
import objects.GameState

class View: Command("view", "view ip/name/pass/hint: View a piece of information about your current computer", false) {

    override fun execute(gameState: GameState, userCommand: String): String {

        return when (userCommand.toLowerCase()) {
            "name" -> messageCreator("Name", gameState.currentComputer.compName)
            "ip" -> messageCreator("Ip Address", gameState.currentComputer.ipAddress)
            "pass" -> messageCreator("Password", gameState.currentComputer.security.password)
            "hint" -> messageCreator("Password Hint", gameState.currentComputer.security.hint)
            else -> gameState.error.notFound(userCommand)
        }
    }

    private fun messageCreator(message: String, variable: String): String {
        return if(variable.isEmpty()) Error().notFound(message, quotes = false)
        else "Computer $message: $variable"
    }
}
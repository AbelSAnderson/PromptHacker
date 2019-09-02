package objects.commands.computerCommands

import objects.Command
import objects.GameState

class Login : Command("login", "login [password]: Login to another computer", false) {

    override fun execute(gameState: GameState, userCommand: String): String {

        val security = gameState.currentComputer.security

        return if (security.isLocked && security.password == userCommand) {
            security.isLocked = false
            "You have successfully logged in to ${gameState.currentComputer.ipAddress}"
        } else if (!security.isLocked)
            gameState.error.loggedIn(true)
        else
            gameState.error.isNot(userCommand, "the correct password")
    }
}
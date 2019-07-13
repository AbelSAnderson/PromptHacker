package objects.commands

import objects.Command
import objects.GameState
import java.util.*

class Set : Command("set", "set name/pass/hint [newValue]: Sets the desired value to a new value", true) {

    override fun execute(gameState: GameState, userCommands: StringTokenizer): String {

        if (userCommands.countTokens() == 2) {

            return when (val valueName = userCommands.nextToken().trim().toLowerCase()) {
                "name" -> {
                    gameState.currentComputer.compName = userCommands.nextToken()
                    "Computer Name is now ${gameState.currentComputer.compName}"
                }
                "pass" -> {
                    gameState.currentComputer.security.password = userCommands.nextToken()
                    "Computer Password is now ${gameState.currentComputer.security.password}"
                }
                "hint" -> {
                    gameState.currentComputer.security.hint = userCommands.nextToken()
                    "Computer Password Hint is now ${gameState.currentComputer.security.hint}"
                }
               else -> gameState.error.notFound(valueName)
            }
        } else {
            return gameState.error.invalidUse(this.name, userCommands)
        }
    }
}
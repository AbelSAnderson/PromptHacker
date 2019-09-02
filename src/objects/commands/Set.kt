package objects.commands

import objects.Command
import objects.GameState
import java.util.*

class Set : Command("set", "set name/pass [newValue]: Sets the desired value to a new value", true) {

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
               else -> gameState.error.objectNotFound(valueName)
            }
        } else {
            return gameState.error.invalidUse(name, userCommands)
        }
    }
}
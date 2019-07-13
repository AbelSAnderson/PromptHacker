package objects

import java.util.*

abstract class Command
(var name: String, var description: String, var isLocked: Boolean) {

    //Methods
    open fun execute(gameState: GameState): String {
        return gameState.error.invalidUse(this.name)
    }

    open fun execute(gameState: GameState, userCommand: String): String {
        return gameState.error.invalidUse(this.name, userCommand)
    }

    open fun execute(gameState: GameState, userCommands: StringTokenizer): String {
        return gameState.error.invalidUse(this.name, userCommands)
    }
}

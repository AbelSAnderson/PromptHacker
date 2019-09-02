package objects.files

import objects.Command
import objects.File
import objects.GameState
import java.util.*

class Executable(fileName: String, var action: Command) : File(fileName) {

    fun execute(gameState: GameState) : String {
        return action.execute(gameState)
    }

    fun execute(gameState: GameState, userCommand: String) : String {
        return action.execute(gameState, userCommand)
    }

    fun execute(gameState: GameState, userCommands: StringTokenizer) : String {
        return action.execute(gameState, userCommands)
    }
}
package objects.commands.hackingCommands

import objects.Command
import objects.GameState

class portCrack : Command("portCrack", "portCrack [portNumber]: Hack into a port. This will usually only works on generic ports.", false) {

    override fun execute(gameState: GameState, userCommand: String): String {
        return ""
    }
}
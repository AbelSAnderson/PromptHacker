package objects.commands.hackingCommands

import objects.Command
import objects.GameState

class portHack : Command("portHack", "portHack [portNumber]: Hack into a port. This will usually only works on generic ports.", false) {

    override fun execute(gameState: GameState, userCommand: String): String {

        gameState.currentComputer.security.ports.find { it.portNumber == userCommand.toInt() } ?: return gameState.error.isNot(userCommand,"a vulnerable port", false)
        gameState.masterPortList.forEach { if(it.key == userCommand.toInt()) return gameState.error.isNot(userCommand, "a hackable port", false) }

        return ""
    }
}
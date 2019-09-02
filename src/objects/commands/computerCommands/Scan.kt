package objects.commands.computerCommands

import objects.Command
import objects.GameState

class Scan : Command("scan", "scan: Shows vulnerable ports on the current computer", false) {

    override fun execute(gameState: GameState): String {
        var portList = "Vulnerable Ports"

        return if (gameState.currentComputer.security.ports.isEmpty()) gameState.error.noObjectsFound("vulnerable ports")
        else {
            gameState.currentComputer.security.ports.forEach { portList += "\n${it.portNumber}: ${it.portName}" }
            portList
        }
    }
}
package objects.commands

import objects.Command
import objects.GameState

class Scan : Command("scan", "scan: Shows exploitable ports on the current computer", false) {

    override fun execute(gameState: GameState): String {
        var portList = "Ports"

        return if (gameState.currentComputer.security.ports.isEmpty()) gameState.error.noObjectsFound("exploitable ports")
        else {
            gameState.currentComputer.security.ports.forEach { portList += "\n${it.portNumber}: ${it.portName}" }
            portList
        }
    }
}
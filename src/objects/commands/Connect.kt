package objects.commands

import objects.Command
import objects.GameState

class Connect : Command("connect", "connect [IpAddress]: Connect to a different computer.", false) {
    override fun execute(gameState: GameState, userCommand: String): String {

        var check = false

        for (computer in gameState.activeComputers) {
            if (computer.ipAddress == userCommand) {
                gameState.currentComputer = computer
                check = true
                break
            }
        }

        return if (check) "Connected to $userCommand."
        else gameState.error.notFound(userCommand)
    }
}
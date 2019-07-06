package objects.commands

import objects.Command
import objects.Error
import objects.GameState

class Connect : Command("connect", "connect [IpAddress]: Connect to a different computer.") {
    override fun execute(gameState: GameState, userCommand: String): String {

        var check = false

        for (computer in gameState.activeComputers) {
            if (computer.ipAddress == userCommand) {
                gameState.currentComputer = computer
                if(gameState.currentComputer.security.password.isNotEmpty())
                    gameState.currentComputer.security.locked = true
                check = true
                break
            }
        }

        return if (check) "Connected to $userCommand."
        else Error().objectNotFound(userCommand)
    }
}
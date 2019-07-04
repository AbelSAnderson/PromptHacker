package objects.commands

import objects.Command
import objects.Error
import objects.GameState

class Connect: Command("connect", "connect [IpAddress]: Connect to a different computer.") {
    override fun execute(gameState: GameState, userCommand: String): String {

        for(computer in gameState.activeComputers) {
            if(computer.ipAddress == userCommand) {
                gameState.currentComputer = computer
                break
            } else if(computer == gameState.activeComputers[gameState.activeComputers.lastIndex]) Error().objectNotFound(userCommand)
        }

        return "Connected to $userCommand."
    }
}
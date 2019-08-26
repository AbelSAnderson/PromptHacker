package objects.commands

import objects.Command
import objects.GameState

class Connect : Command("connect", "connect [IpAddress]: Connect to a different computer.", false) {
    override fun execute(gameState: GameState, userCommand: String): String {
        for (computer in gameState.activeComputers) {
            if (computer.ipAddress == userCommand) {
                gameState.currentComputer.currentFolder = gameState.currentComputer.rootFolder
                gameState.currentComputer = computer

                var temp = "Connected to $userCommand."
                if (gameState.currentComputer.security.isLocked) temp += "\nPlease enter password."

                return temp
            }
        }

        return gameState.error.objectNotFound(userCommand)
    }
}
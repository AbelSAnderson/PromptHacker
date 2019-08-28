package objects.commands

import objects.Command
import objects.GameState

class Connect : Command("connect", "connect [IpAddress]: Connect to a different computer.", false) {
    override fun execute(gameState: GameState, userCommand: String): String {
        gameState.activeComputers.forEach {
            if (it.ipAddress == userCommand || it.compName.toLowerCase() == userCommand.toLowerCase()) {
                gameState.currentComputer.currentFolder = gameState.currentComputer.rootFolder
                gameState.currentComputer = it

                var temp = "Connected to $userCommand."
                if (gameState.currentComputer.security.isLocked) temp += "\nPlease enter password."
                else gameState.currentComputer.security.isLocked = false

                return temp
            }
        }

        return gameState.error.objectNotFound(userCommand)
    }
}
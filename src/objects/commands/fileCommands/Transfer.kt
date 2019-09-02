package objects.commands.fileCommands

import objects.Command
import objects.GameState
import java.util.*

class Transfer : Command("transfer", "transfer [fileName] [computer?]: Transfers a file to your home computer. You can also specify a different computer", true) {
    override fun execute(gameState: GameState, userCommand: String): String {
        val tempFile = gameState.currentComputer.currentFolder.getFile(userCommand)
                ?: return gameState.error.objectNotFound(userCommand)
        gameState.currentComputer.currentFolder.content.remove(tempFile)

        gameState.activeComputers[0].rootFolder.content.add(tempFile)

        return "$userCommand has been successfully transferred to ${gameState.activeComputers[0].compName}."
    }

    override fun execute(gameState: GameState, userCommands: StringTokenizer): String {
        if (userCommands.countTokens() < 3) {
            //Find the File
            val fileName = userCommands.nextToken()
            val tempFile = gameState.currentComputer.currentFolder.getFile(fileName)
                    ?: return gameState.error.objectNotFound(fileName)

            //Find the computer
            val computerId: String = userCommands.nextToken()
            val computerLocation = gameState.activeComputers.find { it.compName == computerId || it.ipAddress == computerId }
                    ?: return gameState.error.objectNotFound(computerId)
            if(computerLocation.security.isLocked) return gameState.error.loggedIn(false, computerId)

            //Remove the original File and Replace it in the destination
            gameState.currentComputer.currentFolder.content.remove(tempFile)
            computerLocation.rootFolder.content.add(tempFile)

            return "$fileName has been successfully transferred to $computerId."

        } else return gameState.error.invalidUse(name, userCommands)
    }
}
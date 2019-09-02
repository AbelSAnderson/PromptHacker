package objects.commands

import objects.Command
import objects.GameState
import objects.files.Folder
import java.util.*

class Transfer : Command("transfer", "transfer [fileName] [computer?] [folderPath?]: Transfers a file to your home computer. You can also specify a different computer and an exact file path.", true) {
    override fun execute(gameState: GameState, userCommand: String): String {
        val tempFile = gameState.currentComputer.currentFolder.getFile(userCommand)
                ?: return gameState.error.objectNotFound(userCommand)
        gameState.currentComputer.currentFolder.content.remove(tempFile)

        gameState.activeComputers[0].rootFolder.content.add(tempFile)

        return "$userCommand has been successfully transferred to ${gameState.activeComputers[0].compName}."
    }

    override fun execute(gameState: GameState, userCommands: StringTokenizer): String {

        if (userCommands.countTokens() < 4) {
            //Find File
            val fileName = userCommands.nextToken()
            val tempFile = gameState.currentComputer.currentFolder.getFile(fileName)
                    ?: return gameState.error.objectNotFound(fileName)

            val computerId: String
            val filePath: String

            if (userCommands.countTokens() == 2) {
                //Find the computer
                computerId = userCommands.nextToken()
                val computerLocation = gameState.activeComputers.find { it.compName == computerId || it.ipAddress == computerId }
                        ?: return gameState.error.objectNotFound(computerId)

                //Locate the File Destination
                filePath = userCommands.nextToken()
                val result = computerLocation.rootFolder.findFolder(gameState, filePath)
                val folderLocation: Folder = result.first ?: return result.second

                //Remove the original File and Replace it in the destination
                gameState.currentComputer.currentFolder.content.remove(tempFile)
                folderLocation.content.add(tempFile)
            } else {
                filePath = userCommands.nextToken()

                //Check if the next command referred to a computer
                if (StringTokenizer(filePath, "/").countTokens() == 1) {
                    val computer = gameState.activeComputers.find { it.compName == filePath || it.ipAddress == filePath }
                    if (computer != null) {
                        gameState.currentComputer.currentFolder.content.remove(tempFile)
                        computer.rootFolder.content.add(tempFile)
                        return "$fileName has been successfully transferred to $filePath"
                    }
                }

                //Locate the File Destination
                val result = gameState.activeComputers[0].rootFolder.findFolder(gameState, filePath)
                val folderLocation: Folder = result.first ?: return result.second

                //Remove the original File and Replace it in the destination
                gameState.currentComputer.currentFolder.content.remove(tempFile)
                folderLocation.content.add(tempFile)

                computerId = gameState.activeComputers[0].compName
            }
            return "$fileName has been successfully transferred to $computerId::/$filePath"

        } else return gameState.error.invalidUse(name, userCommands)
    }
}
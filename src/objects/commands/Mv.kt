package objects.commands

import objects.Command
import objects.GameState
import objects.files.Folder
import java.util.*

class Mv : Command("mv", "mv [fileName] [newLocation]: Moves the selected file to a different folder", true) {

    override fun execute(gameState: GameState, userCommands: StringTokenizer): String {

        return if(userCommands.countTokens() == 2) {

            //Check if the file exists
            val fileName = userCommands.nextToken()
            val temp = gameState.currentComputer.currentFolder.getFile(fileName) ?: return gameState.error.objectNotFound(fileName)

            //Check if the file path is correct
            val filePath = userCommands.nextToken()
            val result = gameState.currentComputer.currentFolder.findFolder(gameState, filePath)
            val currentFolder : Folder = result.first ?: return result.second

            //Move the file to its destination
            gameState.currentComputer.currentFolder.content.remove(temp)
            gameState.currentComputer.currentFolder = currentFolder
            gameState.currentComputer.currentFolder.content.add(temp)

            "'$fileName' has been moved to '$filePath'"
        } else gameState.error.invalidUse(name, userCommands)
    }
}
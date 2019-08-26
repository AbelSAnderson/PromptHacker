package objects.commands

import objects.Command
import objects.GameState
import objects.files.Folder
import java.util.*

class Mv : Command("mv", "mv [fileName] [newLocation]: Moves the selected file to a different folder", true) {

    override fun execute(gameState: GameState, userCommands: StringTokenizer): String {

        return if(userCommands.countTokens() == 2) {

            val fileName = userCommands.nextToken()
            val temp = gameState.currentComputer.currentFolder.findFile(fileName) ?: return gameState.error.objectNotFound(fileName)
            val filePath = userCommands.nextToken()
            val tokenizer = StringTokenizer(filePath, "/")

            var currentFolder = gameState.currentComputer.currentFolder
            var token: String

            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken()

                currentFolder = when (token) {
                    ".." -> currentFolder.parentFolder ?: currentFolder
                    else -> (currentFolder.findFile(token) ?: return gameState.error.objectNotFound(token)) as? Folder ?: return gameState.error.isNot(token, "a folder")
                }
            }

            gameState.currentComputer.currentFolder.content.remove(temp)
            gameState.currentComputer.currentFolder = currentFolder
            gameState.currentComputer.currentFolder.content.add(temp)

            "'$fileName' has been moved to '$filePath'"
        } else gameState.error.invalidUse(name, userCommands)
    }
}
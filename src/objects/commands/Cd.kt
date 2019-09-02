package objects.commands

import objects.Command
import objects.GameState
import objects.files.Folder
import java.util.*

class Cd : Command("cd", "cd [folderName]: Navigate to a specified folder.\ncd ../: Move back one folder", true) {

    override fun execute(gameState: GameState, userCommand: String): String {
        val tokenizer = StringTokenizer(userCommand, "/")
        var currentFolder = gameState.currentComputer.currentFolder
        var token: String

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken()

            currentFolder = when (token) {
                ".." -> currentFolder.parentFolder ?: currentFolder
                else -> (currentFolder.getFile(token) ?: return gameState.error.objectNotFound(token)) as? Folder ?: return gameState.error.isNot(token, "a folder")
            }
        }

        gameState.currentComputer.currentFolder = currentFolder

        return Ls().execute(gameState)
    }
}
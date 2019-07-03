package objects.commands

import objects.Command
import objects.Error
import objects.GameState
import objects.files.Folder
import java.util.*

class Cd : Command("cd", "cd [folderName]: Navigate in to a specified folder.\ncd ../: Move back one folder") {

    override fun execute(gameState: GameState, userCommand: String): String {
        val files = gameState.currentComputer.currentFolder.content
        val tokenizer = StringTokenizer(userCommand, "/")

        for (file in files) {
            if(file.fileName == userCommand) {
                if(file !is Folder) {
                    return Error().notAFolder(userCommand)
                }
                gameState.currentComputer.currentFolder = file
            }
        }

        return Error().invalidFile(userCommand)
    }
}
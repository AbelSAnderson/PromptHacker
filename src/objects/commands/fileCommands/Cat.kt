package objects.commands.fileCommands

import objects.files.Email
import objects.files.Folder
import objects.files.TextFile
import objects.Command
import objects.GameState

class Cat : Command("cat", "Cat [filename]: Display the contents of a file.", true) {

    override fun execute(gameState: GameState, userCommand: String): String {

        val files = gameState.currentComputer.currentFolder

        if(files.content.isEmpty()) return gameState.error.objectNotFound(userCommand, "\nFolder is empty")

        return when (val file = files.getFile(userCommand)) {
            is Email -> file.from + "\n" + file.to + "\n" + file.sent + "\n" + file.subject + "\n" + file.content
            is TextFile -> file.content
            is Folder -> gameState.error.invalidUse("cat", userCommand)
            else -> gameState.error.objectNotFound(userCommand)
        }
    }
}
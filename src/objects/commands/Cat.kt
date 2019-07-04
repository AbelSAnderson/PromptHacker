package objects.commands

import objects.files.Email
import objects.files.Folder
import objects.files.TextFile
import objects.Command
import objects.Error
import objects.GameState

class Cat : Command("cat", "Cat [filename]: Display the contents of a file.") {

    override fun execute(gameState: GameState, userCommand: String): String {

        val files = gameState.currentComputer.currentFolder

        if(files.content.isEmpty()) return Error().objectNotFound(userCommand, "\nFolder is empty")

        return when (val file = files.findFile(userCommand)) {
            is Email -> file.from + "\n" + file.to + "\n" + file.sent + "\n" + file.subject + "\n" + file.content
            is TextFile -> file.content
            is Folder -> Error().wrongUse("cat", "Cd")
            else -> Error().objectNotFound(userCommand)
        }
    }
}
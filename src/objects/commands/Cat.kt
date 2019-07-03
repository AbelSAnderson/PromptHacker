package objects.commands

import objects.files.Email
import objects.files.Folder
import objects.files.TextFile
import objects.Command
import objects.Error
import objects.GameState

class Cat : Command("cat", "Cat [filename]: Display the contents of a file.") {

    override fun execute(gameState: GameState, userCommand: String): String {

        val files = gameState.currentComputer.currentFolder.content

        if(files.isEmpty()) return Error().invalidFile(userCommand, "\nFolder is empty")

        for (file in files) {
            if (file.fileName.toLowerCase() == userCommand.toLowerCase()) {
                when (file) {
                    is Email -> return file.from + "\n" + file.to + "\n" + file.sent + "\n" + file.subject + "\n" + file.content
                    is TextFile -> return file.content
                    is Folder -> return Error().wrongUse("cat", "Cd")
                }
            }
        }

        return Error().invalidFile(userCommand)
    }
}
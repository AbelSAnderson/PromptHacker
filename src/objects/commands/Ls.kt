package objects.commands

import objects.Command
import objects.GameState
import objects.files.Folder

class Ls : Command("ls", "ls: Lists files on the Computer you're currently using.", true) {

    override fun execute(gameState: GameState): String {

        val output = StringBuilder()

        val files = gameState.currentComputer.currentFolder

        output.append(files.fileName + "/")

        for (file in files.content) {
            output.append("\n    " + file.fileName)

            if (file is Folder) output.append("/")
        }

        return output.toString()
    }
}
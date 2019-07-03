package objects.commands

import objects.Command
import objects.GameState

class Ls : Command("ls", "ls: Lists files on the Computer you're currently using.") {

    override fun execute(gameState: GameState): String {

        val output = StringBuilder()

        val files = gameState.currentComputer.currentFolder

        output.append(files.fileName).append("/\n")

        for (file in files.content) {
            output.append("    ").append(file.fileName).append("\n")
        }

        return output.toString()
    }
}
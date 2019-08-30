package objects.commands

import objects.Command
import objects.GameState
import java.util.*

class Transfer : Command("transfer", "transfer [fileName] [computer?] [folderPath?]: Transfers a file to your home computer. You can also specify a different computer and/or an exact file path.", true) {
    override fun execute(gameState: GameState, userCommand: String): String {
        //Pull Rm into a function (fileName, consoleString), and use that for part of this and the next function. Do the same for Mv.
    }

    override fun execute(gameState: GameState, userCommands: StringTokenizer): String {

    }
}
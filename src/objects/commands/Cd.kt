package objects.commands

import objects.Command
import objects.GameState

class Cd : Command("cd", "cd [folderName]: Navigate in to a specified folder.\ncd ../: Move back one folder") {

    override fun execute(gameState: GameState, userCommand: String): String {

        return ""
    }
}
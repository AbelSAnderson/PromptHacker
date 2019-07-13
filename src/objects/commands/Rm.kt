package objects.commands

import objects.Command
import objects.GameState

class Rm : Command("rm", "rm [fileName]: Deletes the selected file", true) {

    override fun execute(gameState: GameState, userCommand: String): String {

        val temp = gameState.currentComputer.currentFolder.findFile(userCommand) ?: return gameState.error.notFound(userCommand)

        gameState.currentComputer.currentFolder.content.remove(temp)

        return "'$userCommand' has been deleted."
    }
}
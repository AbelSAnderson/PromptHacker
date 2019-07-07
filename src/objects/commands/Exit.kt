package objects.commands

import objects.Command
import objects.GameState

class Exit : Command("exit", "exit: Closes the terminal.", false) {

    override fun execute(gameState: GameState): String {
        gameState.isExit = true
        return "Goodbye User."
    }
}
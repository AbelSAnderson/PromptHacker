package objects.commands.globalCommands

import objects.Command
import objects.GameState

class Help : Command("help", "help: Provides a list of all commands.", false) {

    override fun execute(gameState: GameState): String {
        val temp = StringBuilder()

        for (command in gameState.commands) {
            if(temp.isNotEmpty())temp.append("\n")
            temp.append(command.description)
        }

        return temp.toString()
    }
}
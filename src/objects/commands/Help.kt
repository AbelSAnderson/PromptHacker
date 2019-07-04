package objects.commands

import objects.Error
import objects.Command
import objects.GameState

class Help : Command("help", "help: Provides a list of all commands.\nhelp [commandName]: Provide a detailed explanation of that command.") {

    override fun execute(gameState: GameState): String {
        val temp = StringBuilder()

        for (command in gameState.commands) {
            if(temp.isNotEmpty())temp.append("\n")
            temp.append(command.name)
        }

        return temp.toString()
    }

    override fun execute(gameState: GameState, userCommand: String): String {
        var temp :String = Error().unknownCommand(userCommand)

        for (command in gameState.commands) {
            if (command.name == userCommand) temp = command.description
        }

        return temp
    }
}
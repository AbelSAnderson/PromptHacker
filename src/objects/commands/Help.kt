package objects.commands

import objects.Error
import objects.Command
import objects.GameState

class Help : Command("help", "help: Provides a list of all commands.\nhelp [commandName]: Provide a detailed explanation of that command.") {

    override fun execute(gameState: GameState): String {
        val temp = StringBuilder()

        for (command in gameState.commands) {
            temp.append(command.name)
            if (gameState.commands[gameState.commands.size - 1] != command)  temp.append("\n")
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
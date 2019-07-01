package objects

abstract class Command
(var name: String, var description: String) {

    //Methods
    open fun execute(gameState: GameState): String {
        return "Command under development."
    }

    open fun execute(gameState: GameState, userCommand: String): String {
        return Error().invalidUse(this.name, userCommand)
    }
}

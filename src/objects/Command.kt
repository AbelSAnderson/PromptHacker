package objects

abstract class Command
(var name: String, var description: String, var isLocked: Boolean) {

    //Methods
    open fun execute(gameState: GameState): String {
        return Error().invalidUse(this.name)
    }

    open fun execute(gameState: GameState, userCommand: String): String {
        return Error().invalidUse(this.name, " $userCommand")
    }
}

package objects.commands

import objects.Command
import objects.Error
import objects.GameState

class Login : Command("login", "Login [password]: Login to another computer",  false){

    override fun execute(gameState: GameState, userCommand: String): String {

        val security = gameState.currentComputer.security

        return if(security.isLocked && security.password == userCommand){
            security.isLocked = false
            "You have successfully logged in to ${gameState.currentComputer.ipAddress}"
        } else if(!security.isLocked) {
            Error().alreadyLoggedIn()
        } else {
            Error().invalidPass(userCommand)
        }
    }
}
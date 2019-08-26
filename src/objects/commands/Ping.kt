package objects.commands

import objects.Command
import objects.GameState
import java.io.File
import java.lang.StringBuilder

import java.util.Random

class Ping : Command("ping", "ping: Reveals IP addresses currently connected to your network.", false) {

    override fun execute(gameState: GameState): String {

        val temp = StringBuilder()
        var ip: String

        //Loops through the computers connected to it.
        for (compName in gameState.currentComputer.connectedComputers) {

            //Loops through computers already created.
            for (comp in gameState.activeComputers) {

                //Checks if the Computer already exists
                if (compName == comp.compName) {
                    if (temp.isEmpty()) temp.append("Ip(s) found:")
                    temp.append("\n").append("    ").append(comp.ipAddress)
                    break
                } else if (comp == gameState.activeComputers[gameState.activeComputers.lastIndex]) { //If the computer doesn't exist yet, create a new one
                    if (temp.isEmpty()) temp.append("Ip(s) found:")
                    temp.append("\n")

                    ip = generateIP(gameState)
                    gameState.activeComputers.add(gameState.createComputer(File("src/resources/$compName.json"), ip))

                    temp.append("    ").append(ip)
                    break
                }
            }
        }

        if (temp.isEmpty()) temp.append(gameState.error.noObjectsFound("computers"))

        return temp.toString()
    }

    //Duplicate Ips fixed.... Might change this later.
    fun generateIP(gameState: GameState): String {
        val r = Random()

        var newIP = ""

        for (i in 1..4) {
            newIP += r.nextInt(256).toString()
            if (i != 4) newIP += "."
        }

        return if (gameState.ipAddresses.find { newIP == it } == null) {
            gameState.ipAddresses.add(newIP)
            newIP
        } else generateIP(gameState)
    }
}
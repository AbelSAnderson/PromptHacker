package objects.commands

import objects.Command
import objects.Computer
import objects.GameState
import java.io.File
import java.lang.StringBuilder

import java.util.Random

class Ping : Command("ping", "ping: Reveals IP addresses currently connected to your network.", false) {

    override fun execute(gameState: GameState): String {
        val temp = StringBuilder()
        var ip: String
        var newComp: Computer

        if (gameState.currentComputer.connectedComputers.isNotEmpty()) {
            gameState.currentComputer.connectedComputers.forEach {
                if (temp.isEmpty()) temp.append("Ip(s) found:")
                temp.append("\n    ")

                if (it.security.isLocked) temp.append(it.ipAddress)
                else temp.append(it.compName + " (${it.ipAddress})")
            }
        }

        if (gameState.currentComputer.connectedCompNames.isNotEmpty()) {
            gameState.currentComputer.connectedCompNames.forEach {
                if (temp.isEmpty()) temp.append("Ip(s) found:")

                ip = generateIP(gameState)
                newComp = gameState.createComputer(File("src/resources/${it}.json"), ip)
                newComp.connectedComputers.add(gameState.currentComputer)
                gameState.currentComputer.connectedComputers.add(newComp)

                temp.append("\n    $ip")
            }
        }

        gameState.currentComputer.connectedCompNames = emptyArray()

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
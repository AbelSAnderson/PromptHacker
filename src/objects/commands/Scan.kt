package objects.commands

import objects.Command
import objects.GameState
import objects.Error
import java.io.File
import java.lang.StringBuilder

import java.util.Random

class Scan : Command("scan", "scan: Reveals IP addresses currently connected to your network.") {

    override fun execute(gameState: GameState): String {

        val temp = StringBuilder()
        var ip: String

        for(compName in gameState.currentComputer.connectedComputers) {
            for(comp in gameState.activeComputers) {
                if(compName == comp.compName) {
                    if(temp.isEmpty()) temp.append("Ip(s) found:")
                    temp.append("\n").append("    ").append(comp.ipAddress)
                    break
                } else if (comp == gameState.activeComputers[gameState.activeComputers.lastIndex]) {
                    if(temp.isEmpty()) temp.append("Ip(s) found:")
                    temp.append("\n")

                    ip = generateIP()
                    gameState.activeComputers.add(gameState.createComputer(File("src/resources/$compName.json"), ip))

                    temp.append("    ").append(ip)
                    break
                }
            }
        }

        if(temp.isEmpty()) temp.append(Error().noComputers())

        return temp.toString()
    }

    fun generateIP(): String {

        val r = Random()

        var newIP = ""

        newIP += (r.nextInt(899) + 100).toString() + "."
        newIP += (r.nextInt(89) + 10).toString() + "."
        newIP += (r.nextInt(899) + 100).toString() + "."
        newIP += r.nextInt(9)

        return newIP
    }
}
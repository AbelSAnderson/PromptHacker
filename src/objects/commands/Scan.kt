package objects.commands

import objects.Command
import objects.GameState
import objects.Error
import java.io.File
import java.lang.StringBuilder

import java.util.Random

class Scan : Command("scan", "scan: Reveals IP addresses currently connected to your network.", false) {

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

    //This can create duplicate Ips... not going to worry about that right now.
    fun generateIP(): String {
        val r = Random()
        val list = listOf(3, 3, 3, 1)

        var newIP = ""
        var temp: String

        for(num in list) {
            temp = (r.nextInt(("".padStart(num, '9')).toInt()) + 1).toString()
            newIP += temp.padStart(num, '0')
            if(num != 1) newIP += '.'
        }

        return newIP
    }
}
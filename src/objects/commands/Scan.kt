package objects.commands

import objects.Command
import objects.GameState

import java.util.Random

class Scan : Command("scan", "scan: Reveals IP addresses currently connected to your network.") {

    override fun execute(gameState: GameState): String {

        return ""
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
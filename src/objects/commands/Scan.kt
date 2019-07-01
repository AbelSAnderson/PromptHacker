package objects.commands

import objects.Command
import objects.GameState

import java.util.Random

class Scan : Command("scan", "scan: Reveals IP addresses currently connected to your network.") {

    override fun execute(gameState: GameState): String {

        val generatedIPs = Array(7) {""}
        val output = StringBuilder("New IP addresses found:\n")

        generatedIPs.indices.forEach { i ->
            generatedIPs[i] = generateIP()
            output.append("    ").append(generatedIPs[i]).append("\n")
        }

        gameState.connectedIPs = generatedIPs

        return output.toString()
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
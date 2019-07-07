package main

import objects.Error
import objects.GameState

import java.util.Scanner
import java.util.StringTokenizer

/*
    Program Name: Prompt Hacker
    Purpose: A simple hacking game to refresh my skills in Java

    Author: Abel Anderson
    Created: 14/06/2019
    Updated: 01/07/2019

    Notes: Converted project to Kotlin. Implement Array<any>.isLast(index): Boolean as a function
 */
object Game {

    private val gameState = GameState()

    @JvmStatic
    fun main(args: Array<String>) {

        //Scanner
        val input = Scanner(System.`in`)

        //Game Start
        while (!gameState.isExit) {
            print(gameState.currentComputer.ipAddress + "> ")
            println(commandHandler(input.nextLine()) + "\n")
        }

        input.close()
    }

    //Searches commands for user entered Command
    private fun commandHandler(userCommands: String): String {

        val tokenizer = StringTokenizer(userCommands)
        val userCommand = tokenizer.nextToken()

        if (tokenizer.countTokens() < 2) {
            for (command in gameState.commands) {
                if (userCommand.toLowerCase().trim { it <= ' ' } == command.name) {
                    return if(gameState.currentComputer.security.isLocked && command.isLocked) {
                        Error().notLoggedIn()
                    } else if (!tokenizer.hasMoreTokens()) {
                        command.execute(gameState)
                    } else {
                        command.execute(gameState, tokenizer.nextToken().trim { it <= ' ' })
                    }
                }
            }
        }

        return Error().commandNotFound(userCommand)
    }
}
package main

import objects.GameState

import java.util.Scanner
import java.util.StringTokenizer

/*
    Program Name: Prompt Hacker
    Purpose: A simple hacking game to refresh my skills in Java

    Author: Abel Anderson
    Created: 14/06/2019
    Updated: 23/07/2019

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

        if (userCommands.isEmpty()) return gameState.error.objectNotFound(userCommands, gameState.error.defaultMessage)

        val tokenizer = StringTokenizer(userCommands)
        val userCommand = tokenizer.nextToken().toLowerCase().trim()

        for (command in gameState.commands) {
            if (userCommand == command.name) {
                return if (gameState.currentComputer.security.isLocked && command.isLocked) {
                    gameState.error.loggedIn(false)
                } else if (!tokenizer.hasMoreTokens()) {
                    command.execute(gameState)
                } else if (tokenizer.countTokens() < 2) {
                    command.execute(gameState, tokenizer.nextToken().trim())
                } else {
                    command.execute(gameState, tokenizer)
                }
            }
        }

        return gameState.error.objectNotFound(userCommand, gameState.error.defaultMessage)
    }
}
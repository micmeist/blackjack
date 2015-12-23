package de.htwg.tui.view

import de.htwg.core.GameCoreController

import scala.io.StdIn

/**
  * Created by micmeist on 30.10.2015.
  */
object MainMenuTui extends Tui {

  override def printMenu: Unit = {
    print("Press a key to select an option: \n  n: New Game \n  e:Exit \n")
  }

  def proccessUserInput(s: String): Boolean = {
    s match {
      case "n" =>
        GameTui.start(GameCoreController.startNewGame)
        true
      case "e" => false
      case _ => true

    }
  }

  def start: Unit = {
    var continue: Boolean = true
    println("Welcome to Blackjack!")
    while (continue) {
      printMenu
      continue = proccessUserInput(StdIn.readLine())
    }
  }

}

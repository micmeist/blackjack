package de.htwg.tui.view

import de.htwg.core.GameCoreController
import de.htwg.core.entities.Game

import scala.io.StdIn

/**
  * Created by micmeist on 30.10.2015.
  */
object GameTui extends Tui {

  override def printMenu: Unit = {
    print("Press a key to select an option: \n  n: new Round \n  b:back \n")
  }

  def proccessUserInput(s: String, game: Game): Boolean = {
    s match {
      case "n" =>
        RoundTui.start(GameCoreController.startNewRound(game))
        true
      case "b" => false
      case _ => true
    }
  }

  def start(game: Game): Unit = {
    print("New Game started\n")
    var continue: Boolean = true
    while (continue) {
      printMenu
      continue = proccessUserInput(StdIn.readLine(), game)
    }
  }

}

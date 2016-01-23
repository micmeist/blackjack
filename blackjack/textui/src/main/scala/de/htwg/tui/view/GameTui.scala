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

  def proccessUserInput(s: String, game: Game): (Boolean, Game) = {
    s match {
      case "n" =>
        (true, RoundTui.start(GameCoreController.startNewRound(game)))
      case "b" => (false, game)
      case _ => (true, game)
    }
  }

  def start(gameParam: Game): Unit = {
    var game: Game = gameParam
    print("New Game started\n")
    var continue: Boolean = true
    while (continue) {
      printMenu
      val result: Tuple2[Boolean, Game] = proccessUserInput(StdIn.readLine(), game)
      continue = result._1
      game = result._2
    }
  }

}

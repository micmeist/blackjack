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
        val gameResult = RoundTui.start(GameCoreController.startNewRound(game))
        (true, GameCoreController.lost(gameResult))
      case "b" => (false, game)
      case _ => (true, game)
    }
  }

  def start(gameParam: Game): Unit = {
    var game: Game = gameParam
    println("---------New Game started---------")
    var continue: Boolean = true
    while (continue) {
      printMenu
      val result: Tuple2[Boolean, Game] = proccessUserInput(StdIn.readLine(), game)
      game = result._2
      continue = result._1
      if(game.isLost){
        println("---------Game Over!---------")
        continue = false
      }
    }
  }

}

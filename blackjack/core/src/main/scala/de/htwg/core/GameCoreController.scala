package de.htwg.core

import de.htwg.core.entities.{Round, Game}

/**
  * Created by Michael Meister on 17.12.2015.
  */
object GameCoreController {

  def startNewGame: Game = {
    new Game
  }

  def startNewRound (game: Game): Round = {
    new Round(game)
  }
}

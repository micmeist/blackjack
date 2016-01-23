package de.htwg.core

import de.htwg.core.entities.{Game, Round}

/**
  * Created by Michael Meister on 17.12.2015.
  */
object GameCoreController {

  def startNewGame: Game = {
    Game.createGame()
  }

  def startNewRound(game: Game): Round = {
    Round.createRound(game)
  }

  /**
    * Finishes the round: Sets round status to finished, calculates winnings for players and bank and pay them out.
    */
  def finish(round: Round): Round = {
    round.hitBank().finish()
  }

  /**
    * Hit means player gets another card.
    * Adds another card from the game deck to players hand
    */
  def hit(round: Round): Round = {
    round.hit()
  }

  def bet(round: Round, amount: Int): Round = {
    round.bet(amount)
  }

  def lost(game: Game) : Game = {
    game.checkLostGame
  }

}

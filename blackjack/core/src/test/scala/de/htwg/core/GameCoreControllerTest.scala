package de.htwg.core

import de.htwg.core.entities._
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

/**
  * Created by Michael Meister on 24.01.2016.
  */
class GameCoreControllerTest extends FlatSpec with Matchers with BeforeAndAfter {

  var game: Game = null
  var round: Round = null

  before {
    game = Game.createGame()
    round = Round.createRound(game)
  }

  behavior of "GameCoreController"

  it should "return finished Game" in {
    GameCoreController.finish(round).finished should be(true)
  }

  it should "return a new game with deck and players" in {
    val game = GameCoreController.startNewGame
    game.deck.length should be(312)
    game.player.isInstanceOf[HumanPlayer] should be(true)
    game.bank.isInstanceOf[BankPlayer] should be(true)
  }

  it should "retrun a new round with same game and new RoundPlayers" in {
    val round = GameCoreController.startNewRound(game)
    round.game should be(game)
    round.finished should be(false)
    round.humanRoundPlayer.isInstanceOf[RoundPlayer] should be(true)
    round.bankRoundPlayer.isInstanceOf[RoundPlayer] should be(true)
  }

  it should "retrun a new round with a new card for human player" in {
    assert(round.humanRoundPlayer.hand.allCards().length == 2)
    GameCoreController.hit(round).humanRoundPlayer.hand.allCards().length should be(3)
  }

  it should "return a lost game when players money is <= 0" in {
    GameCoreController.lost(Game(game.deck, game.bank, Player(0, "test"))).isLost should be(true)
  }

  it should "not return a lost game when players money is 1" in {
    GameCoreController.lost(Game(game.deck, game.bank, Player(1, "test"))).isLost should be(false)
  }

  it should "return a round with an bet of 10 for player when he bets 10" in {
    GameCoreController.bet(round, 10).humanRoundPlayer.bet.amount should be(10)
  }

  it should "return a round with an bet of 1000 for player when he bets 1001" in {
    GameCoreController.bet(round, 1001).humanRoundPlayer.bet.amount should be(1000)
  }

}

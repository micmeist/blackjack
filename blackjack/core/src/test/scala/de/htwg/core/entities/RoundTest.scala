package de.htwg.core.entities

import de.htwg.core.GameCoreController
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by Michael Meister on 21.12.2015.
  */
class RoundTest extends FlatSpec with Matchers with BeforeAndAfter {

  var game: Game = null
  var round: Round = null

  before {
    game = Game.createGame()
    round = Round.createRound(game)
  }

  "In a new round every Player" should "have two cards" in {
    for (roundPlayer <- round.getRoundPlayers) {
      if (roundPlayer.hand.cards.length != 2) {
        fail()
      }
    }
  }

  "In a new round with 2 players" should "the bank have the second card from the deck as first card" in {
    game = Game.createGame()
    val card = game.deck(1)
    round = Round.createRound(game)
    assert(round.getRoundPlayers.length == 2)
    round.bankRoundPlayer.hand.cards(1) should be(card)
  }

  it should "the first Player have the first card from the deck as first card" in {
    game = Game.createGame()
    val card = game.deck(0)
    round = Round.createRound(game)
    assert(round.getRoundPlayers.length == 2)
    round.humanRoundPlayer.hand.cards(1) should be(card)
  }

  it should "the first Player have the third card from the deck as second card" in {
    game = Game.createGame()
    val card = game.deck(2)
    round = Round.createRound(game)
    assert(round.getRoundPlayers.length == 2)
    round.humanRoundPlayer.hand.cards(0) should be(card)
  }

  "In a Round where Players hand is higher than banks hand" should "player be winner" in {
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 10))
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 9))
    val player: HumanPlayer = new HumanPlayer
    val bank: BankPlayer = new BankPlayer
    round = Round(game, new RoundPlayer(bank, bankHand, new Bet(0)), new RoundPlayer(player, playerHand, new Bet(0)), false)
    round = round.finish()
    round.humanRoundPlayer.winner && !round.bankRoundPlayer.winner should be(true)
  }

  "When a human player bets" should "his bet be 10 if he bets 10" in {
    assert(round.humanRoundPlayer.player.money == 1000)
    round = GameCoreController.bet(round, 10)
    round.humanRoundPlayer.bet.amount should be(10)
    round.humanRoundPlayer.player.money should be(990)
  }

  it should "his bet be 1000 if he bets 1000 and owns 1000" in {
    assert(round.humanRoundPlayer.player.money == 1000)
    round = GameCoreController.bet(round, 1000)
    round.humanRoundPlayer.bet.amount should be(1000)
    round.humanRoundPlayer.player.money should be(0)
  }

  it should "his bet be 1000 if he bets 1001 and owns 1000" in {
    assert(round.humanRoundPlayer.player.money == 1000)
    round = GameCoreController.bet(round, 1001)
    round.humanRoundPlayer.bet.amount should be(1000)
    round.humanRoundPlayer.player.money should be(0)
  }

}

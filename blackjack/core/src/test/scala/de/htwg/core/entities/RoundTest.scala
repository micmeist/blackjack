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
    game =  Game.createGame()
    round = Round.createRound(game)
  }

  "In a new round every Player" should "have two cards" in {
    for (player <- round.getRoundPlayers) {
      if (player.hand.cards.length != 2) {
        fail()
      }
    }
  }

  "In a new round with 2 players" should "the bank have the second card from the deck as first card" in {
    game = Game.createGame()
    val card = game.deck(1)
    round = Round.createRound(game)
    assert(round.getPlayers.length == 2)
    round.bankRoundPlayer.hand.cards(1) should be(card)
  }

  it should "the first Player have the first card from the deck as first card" in {
    game = Game.createGame()
    val card = game.deck(0)
    round = Round.createRound(game)
    assert(round.getPlayers.length == 2)
    round.humanRoundPlayer.hand.cards(1) should be(card)
  }

  it should "the first Player have the third card from the deck as second card" in {
    game = Game.createGame()
    val card = game.deck(2)
    round = Round.createRound(game)
    assert(round.getPlayers.length == 2)
    round.humanRoundPlayer.hand.cards(0) should be(card)
  }

  "In a Round where Players hand is higher than banks hand" should "player be winner" in {
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 10))
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 9))
    val player: HumanPlayer = new HumanPlayer
    val bank: BankPlayer = new BankPlayer
    round = new Round(game, new RoundPlayer(bank, bankHand, new Bet(0)), new RoundPlayer(player, playerHand, new Bet(0)), false)
    //Bank has to be last player
    round.getWinners.contains(player) && !round.getWinners.contains(bank) should be(true)
  }

  "When a human player bets" should "his bet be 10 if he bets 10" in {
    val humanPlayer = round.getPlayers.head.asInstanceOf[HumanPlayer]
    GameCoreController.bet(round, 10)
    round.getBetOfPlayer(humanPlayer).getAmount() should be(10)
  }

}

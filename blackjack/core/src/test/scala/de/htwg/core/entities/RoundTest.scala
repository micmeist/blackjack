package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.collection.mutable

/**
  * Created by Michael Meister on 21.12.2015.
  */
class RoundTest extends FlatSpec with Matchers with BeforeAndAfter {

  var game: Game = null
  var round: Round = null
  var humanPlayer: HumanPlayer = null

  before {
    game = new Game
    round = new Round(game)
    humanPlayer = round.getPlayers.head.asInstanceOf[HumanPlayer]
  }

  "In a new round every Player" should "have two cards" in {
    for (playerAndHand <- round.playersAndHands) {
      if (playerAndHand._2._1.cards.length != 2) {
        fail()
      }
    }
  }

  "In a new round with 2 players" should "the second Player have the second card from the deck as first card" in {
    assert(game.players.length == 2)
    val option: Option[Tuple2[Hand, Bet]] = round.playersAndHands.get(game.players(1))
    option.get._1.cards(1) should be(game.deck(1))
  }

  it should "the first Player have the first card from the deck as first card" in {
    assert(game.players.length == 2)
    val option: Option[Tuple2[Hand, Bet]] = round.playersAndHands.get(game.players.head)
    option.get._1.cards(1) should be(game.deck(0))
  }

  it should "the first Player have the third card from the deck as second card" in {
    assert(game.players.length == 2)
    val option: Option[Tuple2[Hand, Bet]] = round.playersAndHands.get(game.players.head)
    option.get._1.cards(0) should be(game.deck(2))
  }

  "In a Round where Players hand is higher than banks hand" should "player be winner" in {
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 10))
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 9))
    val player: HumanPlayer = new HumanPlayer
    val bank: BankPlayer = new BankPlayer
    round.playersAndHands = mutable.LinkedHashMap((player, (playerHand, new Bet(0))), (bank, (bankHand, new Bet(0))))
    //Bank has to be last player
    assert(round.playersAndHands.last._1.isInstanceOf[BankPlayer])
    round.getWinners.contains(player) && !round.getWinners.contains(bank) should be(true)
  }

  "When banks hand is 20 players hand" should "be winner when its sum is 21" in {
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 10))
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 5, 6))
    round.isPlayerHandWinner(bankHand, playerHand) should be(true)
  }

  it should "not be winner when its sum is 20" in {
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 10))
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 10))
    round.isPlayerHandWinner(bankHand, playerHand) should be(false)
  }

  it should "not be winner when its sum is 19" in {
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 10))
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 9))
    round.isPlayerHandWinner(bankHand, playerHand) should be(false)
  }

  "When banks hand is 21 players hand" should "be winner when its sum is 21" in {
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 5, 6))
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 5, 6))
    round.isPlayerHandWinner(bankHand, playerHand) should be(true)
  }

  it should "not be winner when its sum is 20" in {
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 5, 6))
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 10))
    round.isPlayerHandWinner(bankHand, playerHand) should be(false)
  }

  "When banks hand is 18 players hand" should "be winner when its sum is 19" in {
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 8))
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10, 9))
    round.isPlayerHandWinner(bankHand, playerHand) should be(true)
  }

  it should "not be winner when its sum is 10" in {
    val bankHand: HandBank = TestUtilites.getTestHandBank(Array(10, 9))
    val playerHand: HandHumanPlayer = TestUtilites.getTestHandHumanPlayer(Array(10))
    round.isPlayerHandWinner(bankHand, playerHand) should be(false)
  }

  "When a human player bets" should "his bet be 10 if he bets 10" in {
    round.bet(humanPlayer, 10)
    round.getBetOfPlayer(humanPlayer).getAmount() should be(10)
  }

}

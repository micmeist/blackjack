package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.collection.mutable

/**
  * Created by Michael Meister on 21.12.2015.
  */
class RoundTest extends FlatSpec with Matchers with BeforeAndAfter {

  var game: Game = null
  var round: Round = null

  before {
    game = new Game
    round = new Round(game)
  }

  "In a new round every Player" should "have two cards" in {
    var sum = 0
    for (playerAndHand <- round.playersAndHands) {
      sum += playerAndHand._2.cards.length
    }
    sum should be(4)
  }

  "In a new round with 2 players" should "the second Player have the second card from the deck as first card" in {
    assert(game.players.length == 2)
    val handOption: Option[Hand] = round.playersAndHands.get(game.players(1))
    handOption.get.cards(1) should be(game.deck(1))
  }

  it should "the first Player have the first card from the deck as first card" in {
    assert(game.players.length == 2)
    val handOption: Option[Hand] = round.playersAndHands.get(game.players.head)
    handOption.get.cards(1) should be(game.deck(0))
  }

  it should "the first Player have the third card from the deck as second card" in {
    assert(game.players.length == 2)
    val handOption: Option[Hand] = round.playersAndHands.get(game.players.head)
    handOption.get.cards(0) should be(game.deck(2))
  }

  "In a Round where Players hand is higher than banks hand" should "player be winner" in {
    val playerHand: HandHumanPlayer = new HandHumanPlayer
    playerHand.cards = List(TestUtilites.getTestCard(10), TestUtilites.getTestCard(10))
    val bankHand: HandBank = new HandBank
    bankHand.cards = List(TestUtilites.getTestCard(10), TestUtilites.getTestCard(9))
    val player: HumanPlayer = new HumanPlayer
    val bank: BankPlayer = new BankPlayer
    round.playersAndHands = mutable.Map((bank, bankHand), (player, playerHand))
    //Bank has to be last player
    assert(round.playersAndHands.last._1.isInstanceOf[BankPlayer])
    round.getWinners.contains(player) && !round.getWinners.contains(bank) should be(true)
  }

}

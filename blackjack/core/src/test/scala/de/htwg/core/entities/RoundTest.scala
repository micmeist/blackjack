package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

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
    round = new Round(game)
    var sum = 0
    for (playerAndHand <- round.playersAndHands) {
      sum += playerAndHand._2.cards.length
    }
    sum should be(4)
  }

  "In a new round with 2 players the second Player" should "have the second card from the deck as first card" in {
    assert(game.players.length == 2)
    val handOption: Option[Hand] = round.playersAndHands.get(game.players(1))
    handOption.get.cards(1) should be(game.deck(1))
  }

  "In a new round with 2 players the first Player" should "have the first card from the deck as first card" in {
    assert(game.players.length == 2)
    val handOption: Option[Hand] = round.playersAndHands.get(game.players.head)
    handOption.get.cards(1) should be(game.deck(0))
  }

  it should "have the third card from the deck as second card" in {
    assert(game.players.length == 2)
    val handOption: Option[Hand] = round.playersAndHands.get(game.players.head)
    handOption.get.cards(0) should be(game.deck(2))
  }

}

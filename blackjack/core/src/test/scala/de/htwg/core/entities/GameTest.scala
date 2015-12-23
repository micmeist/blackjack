package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by Michael Meister on 23.12.2015.
  */
class GameTest extends FlatSpec with Matchers with BeforeAndAfter {

  var game: Game = null

  before{
    game = new Game
  }

  "A new game " should "have 312 cards" in {
    game.deck.length should be(312)
  }

  it should "have the bank as last player" in {
    game.players.last.isInstanceOf[BankPlayer] should be(true)
  }

  "When you take a card form the games deck the first card" should "be the head of the deck" in {
    game.getNextCardFromDeck should be(game.deck.head)
  }

}

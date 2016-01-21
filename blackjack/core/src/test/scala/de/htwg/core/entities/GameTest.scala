package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by Michael Meister on 23.12.2015.
  */
class GameTest extends FlatSpec with Matchers with BeforeAndAfter {

  var game: Game = null

  before{
    game = Game.createGame()
  }

  "A new game " should "have 312 cards" in {
    game.deck.length should be(312)
  }

  "When you take a card form the games deck the first card" should "be the head of the deck" in {
    val card = game.deck.head
    game.getNextCardFromDeck should be(card)
  }

}

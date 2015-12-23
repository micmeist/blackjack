package de.htwg.core.entities

import de.htwg.util.GameCardStackFactory

/**
  * Created by micmeist on 29.10.2015.
  */
class Game() {
  val deck: List[Card] = GameCardStackFactory.generateCards
  private val deckIterator: Iterator[Card] = deck.iterator
  //TODO: Add as many human players as user wants
  val players: List[Player] = List(new HumanPlayer, new BankPlayer)

  def getNextCardFromDeck: Card = {
    deckIterator.next()
  }
}

package de.htwg.core.entities

/**
  * Created by Michael Meister on 20.12.2015.
  */
abstract class Hand {

  var cards: List[Card] = Nil

  final def addCardToHand(card: Card): Unit = {
    cards = card :: cards
  }

  /**
    * Checks the sum of the weight of all cards of the hand and the hand is "bust"
    * @return true if sum is > 21, false otherwise
    */
  final def isBust: Boolean = {
    var sum = 0
    if (cards.isEmpty) {
      false
    } else {
      cards.foreach(card => sum += card.getWeight)
      sum > 21
    }
  }

  /**
    * @return the visible cards of the given hand
    */
  def getVisibleCards(): List[Card]

}

class HandBank extends Hand {

  /**
    * Only the second card of banks hand is visible
    * @return the visible card and an unknown card
    */
  override def getVisibleCards(): List[Card] = {
    List(cards.head, new Card("unknown", "unknown", 0))
  }
}

class HandHumanPlayer extends Hand {

  override def getVisibleCards(): List[Card] = {
    cards
  }

}
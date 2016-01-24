package de.htwg.core.entities

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by Michael Meister on 20.12.2015.
  */
abstract class Hand(protected val isBank: Boolean, protected val cards: List[Card]) {

  private[entities] final def addCardToHand(card: Card): Hand = {
    Hand.apply(card :: cards, isBank)
  }

  /**
    * Checks the sum of the weight of all cards of the hand and the hand is "bust"
    * @return true if sum is > 21, false otherwise
    */
  final def isBust: Boolean = {
    if (cards.isEmpty) {
      false
    } else {
      getSum > 21
    }
  }

  final def getSum: Int = {
    var sum = 0
    cards.foreach(card => sum += card.weight)
    sum
  }

  final def isWinnerComparedTo(hand: Hand): Boolean = {
    if (isBust) {
      return false
    }
    if (hand.isBust) {
      return true
    }
    getSum match {
      case 21 => true
      case sum => sum > hand.getSum
    }
  }

  /**
    * @return the all cards of the hand. All cards means cards that are visible to all players during the round and
    *         cards that are not visible to all players
    */
  final def allCards(): List[Card] = {
    cards
  }

  /**
    * @return the cards of the hand which are visible to all players during the round
    */
  def visibleCards(): List[Card]

}

class HandBank(cards: List[Card] = Nil) extends Hand(true, cards) {

  /**
    * Only the second card of banks hand is visible
    * @return the visible card and an unknown card
    */
  override def visibleCards(): List[Card] = {
    List(cards.head, new Card("unknown", "unknown", 0))
  }

}

class HandHumanPlayer(cards: List[Card] = Nil) extends Hand(false, cards) {

  override def visibleCards(): List[Card] = {
    allCards
  }

}

object Hand {
  implicit val handWrites = new Writes[Hand] {
    def writes(hand: Hand) = Json.obj(
      "cards" -> hand.allCards,
      "visibleCards" -> hand.visibleCards,
      "isBust" -> hand.isBust,
      "isBank" -> hand.isBank
    )
  }

  implicit val handReads: Reads[Hand] = (
    (JsPath \ "cards").read[List[Card]] and
      (JsPath \ "isBank").read[Boolean]
    ) (Hand.apply _)

  def apply(cards: List[Card], isBank: Boolean): Hand = {
    if (isBank) {
      new HandBank(cards)
    } else {
      new HandHumanPlayer(cards)
    }
  }
}

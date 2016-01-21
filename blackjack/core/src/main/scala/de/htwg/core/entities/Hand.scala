package de.htwg.core.entities

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by Michael Meister on 20.12.2015.
  */
abstract class Hand {

  var cards: List[Card] = Nil

  private[entities] final def addCardToHand(card: Card): Unit = {
    cards = card :: cards
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
    //TODO: Do this using a scala specific nice way?
    cards.foreach(card => sum += card.getWeight)
    sum
  }

  final def isWinnerComparedTo(hand: Hand): Boolean = {
    if (isBust) {
      return false
    }
    if(hand.isBust){
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
  final def getAllCards(): List[Card] = {
    cards
  }

  /**
    * @return the cards of the hand which are visible to all players during the round
    */
  def getVisibleCards(): List[Card]

  protected def isBank(): Boolean

}

class HandBank extends Hand {

  /**
    * Only the second card of banks hand is visible
    * @return the visible card and an unknown card
    */
  override def getVisibleCards(): List[Card] = {
    List(cards.head, new Card("unknown", "unknown", 0))
  }

  protected override def isBank() = true
}

class HandHumanPlayer extends Hand {

  override def getVisibleCards(): List[Card] = {
    getAllCards
  }

  protected override def isBank() = false
}

object Hand {
  implicit val handWrites = new Writes[Hand] {
    def writes(hand: Hand) = Json.obj(
      "cards" -> hand.getAllCards(),
      "visibleCards" -> hand.getVisibleCards(),
      "isBust" -> hand.isBust,
      "isBank" -> hand.isBank()
    )
  }

  implicit val handReads: Reads[Hand] = (
    (JsPath \ "cards").read[List[Card]] and
      (JsPath \ "isBank").read[Boolean]
    ) (Hand.apply _)

  def apply(cards: List[Card], isBank: Boolean): Hand = {
    var hand: Hand = null
    if (isBank) {
      hand = new HandBank
    } else {
      hand = new HandHumanPlayer
    }
    hand.cards = cards
    hand
  }
}

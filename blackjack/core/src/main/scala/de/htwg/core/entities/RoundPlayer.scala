package de.htwg.core.entities

import play.api.libs.json.Json

/**
  * Created by Michael Meister on 18.01.2016.
  */
case class RoundPlayer (player: Player, hand: Hand, bet: Bet){

  def bet(amount: Int): Unit = {
    var possibleAmount: Int = 0
    if (amount > player.getMoney) {
      possibleAmount = player.getMoney
    } else {
      possibleAmount = amount
    }
    bet + possibleAmount
    player - possibleAmount
  }

  def hit(card: Card): Unit = {
    hand.addCardToHand(card)
  }
}


object RoundPlayer {
  implicit val roundPlayerWrites = Json.writes[RoundPlayer]

  implicit val roundPlayerReads = Json.reads[RoundPlayer]
}

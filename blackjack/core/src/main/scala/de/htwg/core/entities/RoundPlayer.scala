package de.htwg.core.entities

import play.api.libs.json.Json

/**
  * Created by Michael Meister on 18.01.2016.
  */
case class RoundPlayer(player: Player, hand: Hand, bet: Bet, winner: Boolean = false) {

  private[entities] def hasToHit: Boolean = {
    hand.getSum < RoundPlayer.hitBorder
  }

  private[entities] def bet(amount: Int): RoundPlayer = {
    var possibleAmount: Int = 0
    if (amount > player.money) {
      possibleAmount = player.money
    } else {
      possibleAmount = amount
    }
    RoundPlayer(player - possibleAmount, hand, bet + possibleAmount)
  }

  private[entities] def hit(card: Card): RoundPlayer = {
    RoundPlayer(player,hand.addCardToHand(card),bet, winner)
  }

}


object RoundPlayer {

  final val hitBorder: Int = 17

  implicit val roundPlayerWrites = Json.writes[RoundPlayer]

  implicit val roundPlayerReads = Json.reads[RoundPlayer]
}

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
    if (amount > player.money) {
      RoundPlayer(player - player.money, hand, bet + player.money)
    } else {
      RoundPlayer(player - amount, hand, bet + amount)
    }
  }

  private[entities] def hit(card: Card): RoundPlayer = {
    RoundPlayer(player, hand.addCardToHand(card), bet, winner)
  }

}


object RoundPlayer {

  final val hitBorder: Int = 17

  implicit val roundPlayerWrites = Json.writes[RoundPlayer]

  implicit val roundPlayerReads = Json.reads[RoundPlayer]
}

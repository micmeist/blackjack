package de.htwg.core.entities

import play.api.libs.json.Json

/**
  * Created by Michael Meister on 18.01.2016.
  */
case class RoundPlayer (player: Player, hand: Hand, bet: Bet, winner: Boolean = false){

  def bet(amount: Int): Unit = {
    var possibleAmount: Int = 0
    if (amount > player.money) {
      possibleAmount = player.money
    } else {
      possibleAmount = amount
    }
    bet + possibleAmount
    player - possibleAmount
  }

  private[entities] def hit(card: Card): Unit = {
    hand.addCardToHand(card)
  }

  private[entities] def makeWinner(): RoundPlayer ={
    new RoundPlayer(player, hand, bet, true)
  }

}


object RoundPlayer {
  implicit val roundPlayerWrites = Json.writes[RoundPlayer]

  implicit val roundPlayerReads = Json.reads[RoundPlayer]
}

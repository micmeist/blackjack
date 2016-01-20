package de.htwg.core.entities

import play.api.libs.json.Json

/**
  * Created by Michael Meister on 18.01.2016.
  */
case class RoundPlayer (player: Player, hand: Hand, bet: Bet)


object RoundPlayer {
  implicit val roundPlayerWrites = Json.writes[RoundPlayer]

  implicit val roundPlayerReads = Json.reads[RoundPlayer]
}

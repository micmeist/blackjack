package de.htwg.core.entities

import play.api.libs.json.Json

/**
  * Created by Michael Meister on 09.01.2016.
  */
case class Bet(amount: Int) {

  def +(amount: Int): Bet = {
    Bet(this.amount + amount)
  }
}

object Bet {
  implicit val cardWrites = Json.writes[Bet]

  implicit val betReads = Json.reads[Bet]
}

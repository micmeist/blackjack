package de.htwg.core.entities

import play.api.libs.json.Json

/**
  * Created by Michael Meister on 09.01.2016.
  */
case class Bet(private var amount: Int) {

  def +(amount: Int): Unit = {
    this.amount += amount
  }

  def getAmount(): Int = {
    amount
  }
}

object Bet {
  implicit val cardWrites = Json.writes[Bet]

  implicit val cardReads = Json.reads[Bet]
}

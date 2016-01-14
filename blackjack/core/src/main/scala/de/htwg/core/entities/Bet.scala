package de.htwg.core.entities

import play.api.libs.json.{Json, Writes}

/**
  * Created by Michael Meister on 09.01.2016.
  */
class Bet(private var amount: Int) {

  def +(amount: Int): Unit = {
    this.amount += amount
  }

  def getAmount(): Int = {
    amount
  }
}

object Bet {
  implicit val betWrites = new Writes[Bet] {
    def writes(bet: Bet) = Json.obj(
      "amount" -> bet.getAmount()
    )
  }
}

package de.htwg.core.entities

import play.api.libs.json.Json

/**
  * Created by micmeist on 19.10.2015.
  */
case class Card(color: String, number: String, weight: Int) {

  def getWeight: Int = {
    weight
  }

  override def toString: String = color + " " + number

}

object Card {
  implicit val cardWrites = Json.writes[Card]
  
  implicit val cardReads = Json.reads[Card]
}

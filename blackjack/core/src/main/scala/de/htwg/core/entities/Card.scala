package de.htwg.core.entities

/**
 * Created by micmeist on 19.10.2015.
 */
class Card(color: String, number: String, weight: Int) {

  def getWeight: Int = {
    weight
  }

  override def toString: String = color + " " + number

}

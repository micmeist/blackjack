package de.htwg.core.entities

/**
  * Created by Michael Meister on 23.12.2015.
  */
object TestUtilites {
  def getTestCard(weight: Int): Card = {
    new Card("Test", "Test", weight)
  }
}

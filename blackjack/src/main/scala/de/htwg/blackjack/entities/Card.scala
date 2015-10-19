package de.htwg.blackjack.entities

/**
 * Created by micmeist on 19.10.2015.
 */
class Card (color: String, number: String, weight: Int ) {

  def getFullString: String  = "Color: " + color + " Number: " + number + " Weight: " + weight

}

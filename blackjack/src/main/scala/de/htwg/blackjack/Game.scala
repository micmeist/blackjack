package de.htwg.blackjack

import de.htwg.blackjack.entities.Card

/**
 * Created by micmeist on 19.10.2015.
 */
object Game {
  def main(args: Array[String]) {
    val card = new Card("Caro","2",2)
    print(card.getFullString)
  }
}

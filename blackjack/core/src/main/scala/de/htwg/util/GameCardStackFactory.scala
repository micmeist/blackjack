package de.htwg.util

import de.htwg.core.entities.Card

import scala.util.Random

/**
 * Created by micmeist on 29.10.2015.
 */
object GameCardStackFactory {

  def generateCards : List[Card]= {

    var cards: List[Card] = List()
    for (decks <- 0 until 6; weight <- 0 until 13; color <- 0 until 4) {
      cards = new Card(getColor(color), getNumber(weight), getWeight(weight)) :: cards
    }

    def getColor(color: Int): String = color match {
      case 0 => "Kreuz"
      case 1 => "Pik"
      case 2 => "Herz"
      case 3 => "Karo"
    }

    def getNumber(index: Int): String = index match {
      case 0 => "A"
      case 10 => "J"
      case 11 => "Q"
      case 12 => "K"
      case _ => (index + 1).toString
    }

    def getWeight(index: Int): Int = index match {
      case 0 => 11
      case _ => if (index > 9) {
        10
      } else {
        index + 1
      }
    }
    Random.shuffle(cards)
  }
}

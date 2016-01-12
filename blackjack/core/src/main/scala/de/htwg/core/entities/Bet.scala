package de.htwg.core.entities

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

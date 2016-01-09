package de.htwg.core.entities

/**
  * Created by Michael Meister on 09.01.2016.
  */
class Bet(private var amount: Double) {

  def +(amount: Double): Unit = {
    this.amount += amount
  }

  def getAmount(): Double = {
    amount
  }
}

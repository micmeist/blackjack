package de.htwg.core.entities

/**
  * Created by micmeist on 29.10.2015.
  */
abstract class Player(val name: String, private var money: Double) {

  /**
    * Adds the given amount to the money of the player
    *
    * @param amount the amount to add
    */
  def +(amount: Double): Unit = {
    money += amount
  }

  /**
    * Subtracts the given amount to the money of the player
    *
    * @param amount the amount to subtract
    */
  def -(amount: Double): Unit = {
    money -= amount
  }

  def getMoney: Double = {
    money
  }

}

class HumanPlayer extends Player(name = "Human player", money = 1000.0) {

}

class BankPlayer extends Player(name = "Bank", money = 10000000.0) {


}

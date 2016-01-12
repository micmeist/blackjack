package de.htwg.core.entities

import play.api.libs.json.{Json, Writes}

/**
  * Created by micmeist on 29.10.2015.
  */
abstract class Player(val name: String, private var money: Int) {

  /**
    * Adds the given amount to the money of the player
    *
    * @param amount the amount to add
    */
  private[entities] def +(amount: Int): Unit = {
    money += amount
  }

  /**
    * Subtracts the given amount to the money of the player
    *
    * @param amount the amount to subtract
    */
  private[entities] def -(amount: Int): Unit = {
    money -= amount
  }

  def getMoney: Int = {
    money
  }

}

class HumanPlayer extends Player(name = "Human player", money = 1000) {

}

class BankPlayer extends Player(name = "Bank", money = 1000000) {


}

object Player {
  implicit val playerWrites = new Writes[Player] {
    def writes(player: Player) = Json.obj(
      "name" -> player.name,
      "money" -> player.money
    )
  }
}

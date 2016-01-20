package de.htwg.core.entities

import play.api.libs.json.{JsPath, Reads, Json, Writes}
import play.api.libs.functional.syntax._

/**
  * Created by micmeist on 29.10.2015.
  */
abstract class Player(val name: String) {

  protected var money: Int

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

case class HumanPlayer(override var money: Int = 1000) extends Player(name = "Human player")

case class BankPlayer(override var money: Int = 1000000) extends Player(name = "Bank")

object Player {
  implicit val playerWrites: Writes[Player] = new Writes[Player] {
    def writes(player: Player) = Json.obj(
      "name" -> player.name,
      "money" -> player.money
    )
  }

  implicit val playerReads: Reads[Player] = (
    (JsPath \ "money").read[Int] and
      (JsPath \ "name").read[String]
    ) (Player.apply _)

  def apply(money: Int, name: String) = {
    if (name.equalsIgnoreCase("Bank")) {
      new BankPlayer(money)
    } else {
      new HumanPlayer(money)
    }
  }

}

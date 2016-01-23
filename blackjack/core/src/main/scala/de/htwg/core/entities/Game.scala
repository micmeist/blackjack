package de.htwg.core.entities

import de.htwg.util.GameCardStackFactory
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by micmeist on 29.10.2015.
  */
//TODO: Use Iterator for deck
case class Game(var deck: List[Card], bank: Player, player: Player, isLost: Boolean = false) {

  private[entities] def getNextCardFromDeck: Card = {
    val card = deck.head
    deck = deck.filterNot(_.equals(card))
    card
  }

  def getPlayers: List[Player] = {
    List(player, bank)
  }

  private[core] def checkLostGame: Game = {
    if(player.money <= 0){
      Game(deck, bank, player, true)
    }else{
      Game(deck, bank, player, false)
    }
  }

}

object Game {

  def createGame(): Game = {
    val deck: List[Card] = GameCardStackFactory.generateCards
    new Game(deck, new BankPlayer, new HumanPlayer)
  }

  implicit val gameWrites = new Writes[Game] {
    def writes(game: Game) = Json.obj(
      "deck" -> game.deck,
      "bank" -> game.bank,
      "player" -> game.player,
      "players" -> game.getPlayers,
      "isLost" -> game.isLost
    )
  }

  implicit val gameReads: Reads[Game] = (
    (JsPath \ "deck").read[List[Card]] and
      (JsPath \ "bank").read[Player] and
      (JsPath \ "player").read[Player] and
      (JsPath \ "isLost").read[Boolean]
    ) (Game.apply _)


}

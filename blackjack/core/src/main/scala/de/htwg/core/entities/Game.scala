package de.htwg.core.entities

import de.htwg.util.GameCardStackFactory
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by micmeist on 29.10.2015.
  */
class Game() {
  private[entities] var deck: List[Card] = GameCardStackFactory.generateCards
  //TODO: Add as many human players as user wants
  var players: List[Player] = List(new HumanPlayer, new BankPlayer)

  def getNextCardFromDeck: Card = {
    val card = deck.head
    deck = deck.filterNot(_.equals(card))
    card
  }
}

object Game {
  implicit val gameWrites = new Writes[Game] {
    def writes(game: Game) = Json.obj(
      "deck" -> game.deck,
      "players" -> game.players
    )
  }

  implicit val gameReads: Reads[Game] = (
    (JsPath \ "deck").read[List[Card]] and
      (JsPath \ "players").read[List[Player]]
    ) (Game.apply _)

  def apply(deck: List[Card], players: List[Player]) = {
    val game = new Game
    game.deck = deck
    game.players = players
    game
  }
}

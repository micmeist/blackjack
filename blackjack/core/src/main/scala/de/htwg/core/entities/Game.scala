package de.htwg.core.entities

import de.htwg.util.GameCardStackFactory
import play.api.libs.json.{Writes, Json}

/**
  * Created by micmeist on 29.10.2015.
  */
class Game() extends Serializable{
  private[entities] var deck: List[Card] = GameCardStackFactory.generateCards
  //TODO: Add as many human players as user wants
  val players: List[Player] = List(new HumanPlayer, new BankPlayer)

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
}

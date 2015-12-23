package de.htwg.core.entities

import scala.collection.mutable

/**
  * Created by Michael Meister on 19.12.2015.
  */
class Round(game: Game) {

  var playersAndHands: mutable.Map[Player, Hand] = null
  createPlayersHands
  deal

  def getPlayers: List[Player] = {
    game.players
  }

  def getHandOfPlayer(player: Player): Hand = {
    var handOption: Option[Hand] = playersAndHands.get(player)
    handOption.get
  }

  @deprecated
  def nextCardHumanPlayer: Unit = {
    nextCardForPlayer(game.players(1))
  }

  @deprecated
  def nextCardBank: Unit = {
    nextCardForPlayer(game.players.head)
  }

  def nextCardForPlayer(player: Player): Unit = {
    val handOption: Option[Hand] = playersAndHands.get(player)
    handOption.get.addCardToHand(game.getNextCardFromDeck)
  }

  def hit = {
    nextCardHumanPlayer
  }

  private def deal: Unit = {
    for (x <- 0 to 1) {
      for (playerAndHand <- playersAndHands) {
        playerAndHand._2.addCardToHand(game.getNextCardFromDeck)
      }
    }
  }

  private def createPlayersHands: Unit = {
    playersAndHands = mutable.Map()
    for (player <- game.players) {
      var hand: Hand = null
      player match {
        case a: BankPlayer => hand = new HandBank
        case b: HumanPlayer => hand = new HandHumanPlayer
      }
      playersAndHands += (player -> hand)
    }

  }

}

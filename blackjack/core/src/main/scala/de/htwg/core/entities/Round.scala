package de.htwg.core.entities

import scala.collection.mutable

/**
  * Created by Michael Meister on 19.12.2015.
  */
class Round(game: Game) {

  //TODO: use immutable List
  var playersAndHands: mutable.Map[Player, Hand] = null
  deal

  //TODO: Some Players may be not in round
  def getPlayers: List[Player] = {
    game.players
  }

  def getHandOfPlayer(player: Player): Hand = {
    val handOption: Option[Hand] = playersAndHands.get(player)
    handOption.get
  }

  /**
    * Hit means player gets another card.
    * Adds another card from the game deck to players hand
    * @param player the player who gets another card to his hand
    */
  def hit(player: Player): Unit = {
    val handOption: Option[Hand] = playersAndHands.get(player)
    handOption.get.addCardToHand(game.getNextCardFromDeck)
  }

  def getWinners: List[Player] = {
    val banksHand: Hand = playersAndHands.last._2
    val winners: List[Player] = List()
    for (playerAndHand <- playersAndHands) {
      if (!playerAndHand._1.isInstanceOf[BankPlayer] && !playerAndHand._2.isBust) {
        playerAndHand._2.getSum match {
          case 21 => playerAndHand._1 :: winners
          case sum =>
            if(sum > banksHand.getSum){
              playerAndHand._1 :: winners
            }
        }
      }
    }
    winners
  }

  private def deal: Unit = {
    createPlayersHands
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

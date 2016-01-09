package de.htwg.core.entities

import scala.collection.mutable

/**
  * Created by Michael Meister on 19.12.2015.
  */
class Round(game: Game) {

  //TODO: use immutable List
  private[entities] var playersAndHands: mutable.LinkedHashMap[Player, Tuple2[Hand, Bet]] = null
  deal

  //TODO: Some Players may be not in round
  def getPlayers: List[Player] = {
    game.players
  }

  def getHandOfPlayer(player: Player): Hand = {
    val option: Option[Tuple2[Hand, Bet]] = playersAndHands.get(player)
    option.get._1
  }

  def getHandOfBank(): HandBank = {
    val option: Option[Tuple2[Hand, Bet]] = playersAndHands.get(getPlayers.last)
    option.get._1.asInstanceOf[HandBank]
  }

  def getBetOfPlayer(player: HumanPlayer): Bet = {
    val option: Option[Tuple2[Hand, Bet]] = playersAndHands.get(player)
    option.get._2
  }

  def bet(player: HumanPlayer, amount: Double): Unit = {
    playersAndHands.get(player).get._2 + amount
    player - amount
  }

  /**
    * Hit means player gets another card.
    * Adds another card from the game deck to players hand
    * @param player the player who gets another card to his hand
    */
  def hit(player: Player): Unit = {
    val handOption: Option[Tuple2[Hand, Bet]] = playersAndHands.get(player)
    handOption.get._1.addCardToHand(game.getNextCardFromDeck)
  }

  def getWinners: List[Player] = {
    val banksHand: HandBank = playersAndHands.last._2._1.asInstanceOf[HandBank]
    var winners: List[Player] = List()
    playersAndHands.foreach(playerAndHand =>
      if (!playerAndHand._1.isInstanceOf[BankPlayer]) {
        if (isPlayerHandWinner(banksHand, playerAndHand._2._1.asInstanceOf[HandHumanPlayer])) {
          winners = playerAndHand._1 :: winners
        }
      }
    )
    winners
  }

  private[entities] def isPlayerHandWinner(banksHand: HandBank, playerHand: HandHumanPlayer): Boolean = {
    if (playerHand.isBust) {
      return false
    }
    playerHand.getSum match {
      case 21 => true
      case sum =>
        if (sum > banksHand.getSum) {
          true
        } else {
          false
        }
    }
  }

  private def deal: Unit = {
    createPlayersHands
    for (x <- 0 to 1) {
      for (playerAndHand <- playersAndHands) {
        playerAndHand._2._1.addCardToHand(game.getNextCardFromDeck)
      }
    }
  }

  private def createPlayersHands: Unit = {
    playersAndHands = mutable.LinkedHashMap()
    for (player <- game.players) {
      var hand: Hand = null
      player match {
        case a: BankPlayer => hand = new HandBank
        case b: HumanPlayer => hand = new HandHumanPlayer
      }
      playersAndHands += (player ->(hand, new Bet(0)))
    }
  }

}

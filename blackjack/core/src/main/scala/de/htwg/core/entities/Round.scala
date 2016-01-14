package de.htwg.core.entities

import play.api.libs.json.{Json, Writes}
import scala.collection.mutable.LinkedHashMap

/**
  * Created by Michael Meister on 19.12.2015.
  */
class Round(private val game: Game) {

  private var finished: Boolean = false

  //TODO: use immutable List
  private[entities] var playersAndHandsAndBets: LinkedHashMap[Player, Tuple2[Hand, Bet]] = null
  deal

  //TODO: Some Players may be not in round
  def getPlayers: List[Player] = {
    game.players
  }

  def getHandOfPlayer(player: Player): Hand = {
    val option: Option[Tuple2[Hand, Bet]] = playersAndHandsAndBets.get(player)
    option.get._1
  }

  def getHandOfBank(): HandBank = {
    val option: Option[Tuple2[Hand, Bet]] = playersAndHandsAndBets.get(getBank)
    option.get._1.asInstanceOf[HandBank]
  }

  private def getBank(): BankPlayer = {
    getPlayers.last.asInstanceOf[BankPlayer]
  }

  def getBetOfPlayer(player: HumanPlayer): Bet = {
    val option: Option[Tuple2[Hand, Bet]] = playersAndHandsAndBets.get(player)
    option.get._2
  }

  //TODO: Test für Fall, dass amount höher als verfügbares Geld
  def bet(player: HumanPlayer, amount: Int): Unit = {
    var possibleAmount: Int = 0
    if (amount > player.getMoney) {
      possibleAmount = player.getMoney
    } else {
      possibleAmount = amount
    }
    playersAndHandsAndBets.get(player).get._2 + possibleAmount
    player - possibleAmount
  }

  /**
    * Hit means player gets another card.
    * Adds another card from the game deck to players hand
    * @param player the player who gets another card to his hand
    */
  def hit(player: Player): Unit = {
    val handOption: Option[Tuple2[Hand, Bet]] = playersAndHandsAndBets.get(player)
    handOption.get._1.addCardToHand(game.getNextCardFromDeck)
  }

  /**
    * Finishes the round: Sets round status to finished, calculates winnings for players and bank and pay them out.
    */
  def finish(): Unit = {
    if (!finished) {
      val winners: List[Player] = getWinners
      for (player <- getPlayers) {
        val cash = playersAndHandsAndBets.get(player).get._2.getAmount()
        if (winners.contains(player)) {
          player + cash * 2
          getBank() - cash * 2
        } else {
          getBank() + cash
        }
      }
      finished = true
    }
  }

  /**
    * @return the list of players who have an higher hand than the bank
    */
  def getWinners: List[Player] = {
    val banksHand: HandBank = playersAndHandsAndBets.last._2._1.asInstanceOf[HandBank]
    var winners: List[Player] = List()
    playersAndHandsAndBets.foreach(playerAndHand =>
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
      for (playerAndHandAndBet <- playersAndHandsAndBets) {
        playerAndHandAndBet._2._1.addCardToHand(game.getNextCardFromDeck)
      }
    }
  }

  private def createPlayersHands: Unit = {
    playersAndHandsAndBets = LinkedHashMap()
    for (player <- game.players) {
      var hand: Hand = null
      player match {
        case a: BankPlayer => hand = new HandBank
        case b: HumanPlayer => hand = new HandHumanPlayer
      }
      playersAndHandsAndBets += (player ->(hand, new Bet(0)))
    }
  }

}

object Round {

  implicit def tuple2Writes[Hand, Bet](implicit handWrites : Writes[Hand], betWrites : Writes[Bet]) : Writes[Tuple2[Hand, Bet]] = new Writes[Tuple2[Hand, Bet]] {
    def writes(tuple: Tuple2[Hand, Bet]) = Json.obj(
      "hand" -> tuple._1,
      "bet" -> tuple._2
    )
  }
  implicit val roundWrites = new Writes[Round] {
    def writes(round: Round) = Json.obj(
      "game" -> round.game,
      "playersAndHandsAndBets" -> Json.toJson(round.playersAndHandsAndBets.toMap[Player, Tuple2[Hand, Bet]])
    )
  }

}

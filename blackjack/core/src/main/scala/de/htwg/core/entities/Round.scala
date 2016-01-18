package de.htwg.core.entities

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._


import scala.collection.immutable.Map

/**
  * Created by Michael Meister on 19.12.2015.
  */
class Round(private val game: Game) {

  private var finished: Boolean = false

  //TODO: use immutable List
  private[entities] var playersAndHandsAndBets: Map[Player, HandAndBet] = null
  deal

  //TODO: Some Players may be not in round
  def getPlayers: List[Player] = {
    game.players
  }

  def getRoundPlayers: List[RoundPlayer] = {
    var result: List[RoundPlayer] = List()
    for (player <- getPlayers) {
      //TODO: use Option
      val handAndBet = playersAndHandsAndBets.get(player).get
      result = new RoundPlayer(player, handAndBet._1, handAndBet._2) :: result
    }
    result
  }

  def getHandOfPlayer(player: Player): Hand = {
    val option: Option[HandAndBet] = playersAndHandsAndBets.get(player)
    option.get._1
  }

  def getHandOfBank(): HandBank = {
    val option: Option[HandAndBet] = playersAndHandsAndBets.get(getBank)
    option.get._1.asInstanceOf[HandBank]
  }

  private def getBank(): BankPlayer = {
    getPlayers.last.asInstanceOf[BankPlayer]
  }

  def getBetOfPlayer(player: HumanPlayer): Bet = {
    val option: Option[HandAndBet] = playersAndHandsAndBets.get(player)
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
    val handOption: Option[HandAndBet] = playersAndHandsAndBets.get(player)
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
    val banksHand: HandBank = playersAndHandsAndBets.get(getPlayers.last).get._1.asInstanceOf[HandBank]
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
      for (player <- getPlayers) {
        val handAndBet: HandAndBet = playersAndHandsAndBets.get(player).get
        handAndBet._1.addCardToHand(game.getNextCardFromDeck)
      }
    }
  }

  private def createPlayersHands: Unit = {
    playersAndHandsAndBets = Map()
    for (player <- game.players) {
      var hand: Hand = null
      player match {
        case a: BankPlayer => hand = new HandBank
        case b: HumanPlayer => hand = new HandHumanPlayer
      }
      playersAndHandsAndBets += (player -> new HandAndBet(hand, new Bet(0)))
    }
  }

}

object Round {

  implicit def tuple2Writes[Player, HandAndBet](implicit aWrites: Writes[Player], bWrites: Writes[HandAndBet]): Writes[Tuple2[Player, HandAndBet]] = new Writes[Tuple2[Player, HandAndBet]] {
    def writes(tuple: Tuple2[Player, HandAndBet]) = Json.obj(
      "player" -> tuple._1,
      "handAndBet" -> tuple._2
    )
  }

  implicit def tuple2Reads[Player, HandAndBet](implicit aReads: Reads[Player], bReads: Reads[HandAndBet]): Reads[Tuple2[Player, HandAndBet]] = new Reads[Tuple2[Player, HandAndBet]] {
    def reads(json: JsValue) = {
      json match {
        case JsObject(obj) => for {
          a <- aReads.reads(obj.get("player").get)
          b <- bReads.reads(obj.get("handAndBet").get)
        } yield (a, b)
        case _ => JsError(ValidationError("Expected array of two elements"))
      }
    }
  }

  implicit val roundWrites = new Writes[Round] {
    def writes(round: Round) = Json.obj(
      "game" -> round.game,
      "playersAndHandsAndBets" -> round.playersAndHandsAndBets.toList
    )
  }

  implicit val roundReads: Reads[Round] = (
    (JsPath \ "game").read[Game] and
      (JsPath \ "playersAndHandsAndBets").read[List[(Player, HandAndBet)]]
    ) (Round.apply _)

  def apply(game: Game, playersAndHandsAndBets: List[(Player, HandAndBet)]): Round = {
    val round = new Round(game)
    round.playersAndHandsAndBets = Map()
    playersAndHandsAndBets.foreach(item =>
      round.playersAndHandsAndBets += item._1 -> item._2
    )
    round
  }


}

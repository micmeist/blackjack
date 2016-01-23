package de.htwg.core.entities

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by Michael Meister on 19.12.2015.
  */
case class Round(game: Game, bankRoundPlayer: RoundPlayer, humanRoundPlayer: RoundPlayer, finished: Boolean) {

  @deprecated
  def getPlayers: List[Player] = {
    game.getPlayers
  }

  def getRoundPlayers: List[RoundPlayer] = {
    List(humanRoundPlayer, bankRoundPlayer)
  }

  @deprecated
  def getHandOfPlayer(player: Player): Hand = {
    player match {
      case a: BankPlayer => bankRoundPlayer.hand
      case b: HumanPlayer => humanRoundPlayer.hand
    }
  }

  @deprecated
  def getHandOfBank(): HandBank = {
    bankRoundPlayer.hand.asInstanceOf[HandBank]
  }

  @deprecated
  private def getBank(): BankPlayer = {
    game.bank.asInstanceOf[BankPlayer]
  }

  @deprecated
  def getBetOfPlayer(player: HumanPlayer): Bet = {
    humanRoundPlayer.bet
  }

  //TODO: Test für Fall, dass amount höher als verfügbares Geld
  private[core] def bet(amount: Int): Round = {
    humanRoundPlayer.bet(amount)
    this
  }

  /**
    * Hit means player gets another card.
    * Adds another card from the game deck to players hand
    */
  private[core] def hit(): Round = {
    humanRoundPlayer.hit(game.getNextCardFromDeck)
    this
  }

  private[core] def hitBank(): Round = {
    while (bankRoundPlayer.hasToHit) {
      bankRoundPlayer.hit(game.getNextCardFromDeck)
    }
    this
  }

  /**
    * Finishes the round: Sets round status to finished, calculates winnings for players and bank and pay them out.
    */
  def finish(): Round = {
    if (!finished) {
      val cash = humanRoundPlayer.bet.getAmount()
      if (humanRoundPlayer.hand isWinnerComparedTo bankRoundPlayer.hand) {
        val human = RoundPlayer(humanRoundPlayer.player + cash * 2, humanRoundPlayer.hand, humanRoundPlayer.bet, true)
        val bank = RoundPlayer(bankRoundPlayer.player - cash * 2, bankRoundPlayer.hand, bankRoundPlayer.bet)
        return Round(Game(game.deck, bank.player, human.player), bank, human, true)
      } else {
        val bank = RoundPlayer(bankRoundPlayer.player + cash, bankRoundPlayer.hand, bankRoundPlayer.bet, true)
        return Round(Game(game.deck, bank.player, humanRoundPlayer.player), bank, humanRoundPlayer, true)
      }
    }
    Round(game, bankRoundPlayer, humanRoundPlayer, true)
  }

  /**
    * @return the list of players who have an higher hand than the bank
    */
  @deprecated
  def getWinners: List[Player] = {
    val banksHand: Hand = bankRoundPlayer.hand
    if (humanRoundPlayer.hand isWinnerComparedTo banksHand) {
      List(humanRoundPlayer.player)
    } else {
      List()
    }
  }


}

object Round {

  def createRound(game: Game): Round = {
    val round = new Round(game, createRoundPlayer(game.bank), createRoundPlayer(game.player), false)
    deal(game, round)
    round
  }

  private def deal(game: Game, round: Round): Unit = {
    for (x <- 0 to 1) {
      for (player <- round.getRoundPlayers) {
        player.hand.addCardToHand(game.getNextCardFromDeck)
      }
    }
  }

  private def createRoundPlayer(player: Player): RoundPlayer = {
    var hand: Hand = null
    player match {
      case a: BankPlayer => hand = new HandBank
      case b: HumanPlayer => hand = new HandHumanPlayer
    }
    new RoundPlayer(player, hand, new Bet(0))

  }

  implicit val roundWrites = new Writes[Round] {
    def writes(round: Round) = Json.obj(
      "game" -> round.game,
      "bankRoundPlayer" -> round.bankRoundPlayer,
      "humanRoundPlayer" -> round.humanRoundPlayer,
      "finished" -> round.finished
    )
  }

  implicit val roundReads: Reads[Round] = (
    (JsPath \ "game").read[Game] and
      (JsPath \ "bankRoundPlayer").read[RoundPlayer] and
      (JsPath \ "humanRoundPlayer").read[RoundPlayer] and
      (JsPath \ "finished").read[Boolean]
    ) (Round.apply _)


}

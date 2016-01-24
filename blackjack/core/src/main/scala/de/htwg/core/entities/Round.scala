package de.htwg.core.entities

import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.collection.mutable

/**
  * Created by Michael Meister on 19.12.2015.
  */
case class Round(game: Game, bankRoundPlayer: RoundPlayer, humanRoundPlayer: RoundPlayer, finished: Boolean = false) {

  def getRoundPlayers: List[RoundPlayer] = {
    List(humanRoundPlayer, bankRoundPlayer)
  }

  //TODO: Test für Fall, dass amount höher als verfügbares Geld
  private[core] def bet(amount: Int): Round = {
    Round(game, bankRoundPlayer, humanRoundPlayer.bet(amount))
  }

  /**
    * Hit means player gets another card.
    * Adds another card from the game deck to players hand
    */
  private[core] def hit(): Round = {
    Round(game, bankRoundPlayer, humanRoundPlayer.hit(game.getNextCardFromDeck))
  }

  private[core] def hitBank(): Round = {
    var resultBankRoundPlayer: RoundPlayer = bankRoundPlayer
    while (resultBankRoundPlayer.hasToHit) {
      resultBankRoundPlayer = resultBankRoundPlayer.hit(game.getNextCardFromDeck)
    }
    Round(game, resultBankRoundPlayer, humanRoundPlayer)
  }

  /**
    * Finishes the round: Sets round status to finished, calculates winnings for players and bank and pay them out.
    */
  def finish(): Round = {
    if (!finished) {
      val cash = humanRoundPlayer.bet.amount
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


}

object Round {

  def createRound(game: Game): Round = {
    val map: mutable.Map[Player, Hand] = deal(game, game.getPlayers)
    Round(game, createRoundPlayer(game.bank, map.get(game.bank).get), createRoundPlayer(game.player, map.get(game.player).get), false)
  }

  private def deal(game: Game, players: List[Player]): mutable.Map[Player, Hand] = {
    val map: mutable.Map[Player, Hand] = mutable.Map()
    players.foreach(player => map.put(player, createHand(player)))

    for (x <- 0 to 1; player <- players) {
      val hand: Hand = map.get(player).get
      map.put(player, hand.addCardToHand(game.getNextCardFromDeck))
    }
    map
  }

  private def createRoundPlayer(player: Player, hand: Hand): RoundPlayer = {
    new RoundPlayer(player, hand, new Bet(0))
  }

  private def createHand(player: Player): Hand = {
    var hand: Hand = null
    player match {
      case a: BankPlayer => new HandBank
      case b: HumanPlayer => new HandHumanPlayer
    }
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

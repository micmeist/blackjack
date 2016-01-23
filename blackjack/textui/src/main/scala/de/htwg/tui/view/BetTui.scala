package de.htwg.tui.view

import de.htwg.core.GameCoreController
import de.htwg.core.entities.{BankPlayer, Player, Round}

import scala.io.StdIn

/**
  * Created by Michael Meister on 09.01.2016.
  */
object BetTui extends Tui {

  override def printMenu: Unit = {
    println("Insert the amount to bet.")
  }

  def proccessUserInput(d: Int, round: Round, player: Player): (Boolean, Round) = {
    d match {
      case amount =>
        (false, GameCoreController.bet(round, amount))
      case _ => (true, round)
    }
  }

  def start(roundParam: Round): Round = {
    var round: Round = roundParam
    for (roundPlayer <- round.getRoundPlayers) {
      if (!roundPlayer.player.isInstanceOf[BankPlayer]) {
        var continue: Boolean = true
        while (continue) {
          println(roundPlayer.player.name + " you have " + roundPlayer.player.money + " make your bet.")
          printMenu
          val result: Tuple2[Boolean, Round] = proccessUserInput(StdIn.readInt(), round, roundPlayer.player)
          continue = result._1
          round = result._2
        }
      }
    }
    round
  }
}

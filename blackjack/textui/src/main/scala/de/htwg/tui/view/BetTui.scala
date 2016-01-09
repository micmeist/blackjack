package de.htwg.tui.view

import de.htwg.core.entities.{BankPlayer, HumanPlayer, Round}

import scala.io.StdIn

/**
  * Created by Michael Meister on 09.01.2016.
  */
object BetTui extends Tui{

  override def printMenu: Unit = {
    println("Insert the amount to bet.")
  }

  def proccessUserInput(d: Double, round: Round, player: HumanPlayer): Boolean = {
    d match {
      case amount =>
        round.bet(player,amount)
        false
      case _ => true
    }
  }

  def start (round: Round): Unit ={
    for (player <- round.getPlayers) {
      if (!player.isInstanceOf[BankPlayer]) {
        var continue: Boolean = true
        while (continue) {
          println("Player " + player.name + " make your bet.")
          printMenu
          continue = proccessUserInput(StdIn.readDouble(), round, player.asInstanceOf[HumanPlayer])
        }
      }
    }
  }
}

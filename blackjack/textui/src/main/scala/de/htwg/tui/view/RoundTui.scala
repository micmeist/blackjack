package de.htwg.tui.view

import de.htwg.core.GameCoreController
import de.htwg.core.entities._

import scala.io.StdIn

/**
  * Created by micmeist on 30.10.2015.
  */
object RoundTui extends Tui {

  override def printMenu: Unit = {
    println("Press a key to select an option: \n  h: hit \n s: stand")
  }

  def printVisibleCardsOfPlayers(round: Round): Unit = {
    println("Cards:")
    for (roundPlayer <- round.getRoundPlayers) {
      println(roundPlayer.player.name + " " + roundPlayer.hand.visibleCards)
    }
  }

  def printAllCardsAndBetsOfPlayers(round: Round): Unit = {
    println("Name | Cards | Bet")
    for (roundPlayer <- round.getRoundPlayers) {
      print(roundPlayer.player.name + " | " + roundPlayer.hand.allCards())
      if (roundPlayer.player.isInstanceOf[HumanPlayer]) {
        print(" | " + roundPlayer.bet.amount)
      }
      println()
    }
  }

  def printWinners(round: Round): Unit = {
    if (round.humanRoundPlayer.winner) {
      println(round.humanRoundPlayer.player.name + " has won")
    } else {
      println("Bank has won")
    }
  }

  def proccessUserInput(s: String, roundParam: Round, player: Player): (Boolean, Round) = {
    var round = roundParam
    s match {
      case "h" => round = GameCoreController.hit(round)
        if (round.humanRoundPlayer.hand.isBust) {
          println("Player " + player.name + ", your hand is bust")
          (false, round)
        } else {
          (true, round)
        }
      case "s" => (false, round)
      case _ => (true, round)
    }
  }

  def start(roundParam: Round): Game = {
    var round = roundParam
    println("---------New Round started---------")

    /**
      * Das Blackjack-Spiel beginnt mit dem Einsatz der Spieler.
      */
    round = BetTui.start(round)

    /**
      * Dann beginnt das Blackjack-Spiel mit dem Austeilen der Karten.
      * Dealer fragt jeden Spieler von links nach rechts, wie sie fortfahren möchten. Abhängig von ihren Karten können
      * Spieler entweder eine weitere Karte fordern (hit) oder sie können weitere Karten ablehnen (stand).  Spieler
      * können so viele weitere Karten fordern, wie sie möchten, aber sobald sie die 21 überschreiten, ist die Hand
      * “bust” und der Einsatz wird vom Dealer eingesammelt.
      */
    for (roundPlayer <- round.getRoundPlayers) {
      if (!roundPlayer.player.isInstanceOf[BankPlayer]) {
        var continue: Boolean = true
        while (continue) {
          printVisibleCardsOfPlayers(round)
          println("Player " + roundPlayer.player.name + ", what do you want to do?")
          printMenu
          val result: Tuple2[Boolean, Round]  = proccessUserInput(StdIn.readLine(), round, roundPlayer.player)
          continue = result._1
          round = result._2
        }
      }
    }
    round = GameCoreController.finish(round)
    printAllCardsAndBetsOfPlayers(round)
    printWinners(round)
    round.game
  }
}

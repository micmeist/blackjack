package de.htwg.tui.view

import de.htwg.core.entities.{HumanPlayer, Player, Round, BankPlayer}

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
    for (player <- round.getPlayers) {
      println(player.name + " " + round.getHandOfPlayer(player).getVisibleCards)
    }
  }

  def printAllCardsAndBetsOfPlayers(round: Round): Unit = {
    println("Name | Cards | Bet")
    for (player <- round.getPlayers) {
      print(player.name + " | " + round.getHandOfPlayer(player).getAllCards)
      if (player.isInstanceOf[HumanPlayer]) {
        print(" | " + round.getBetOfPlayer(player.asInstanceOf[HumanPlayer]).getAmount())
      }
      println()
    }
  }

  def printWinners(round: Round): Unit = {
    val winners: List[Player] = round.getWinners
    if (winners.nonEmpty) {
      println("Winners in this round:")
      for (winner <- winners) {
        println(winner.name)
      }
    } else {
      println("There are no winners in this round")
    }
  }

  def proccessUserInput(s: String, round: Round, player: Player): Boolean = {
    s match {
      case "h" => round.hit(player)
        if (round.getHandOfPlayer(player).isBust) {
          println("Player " + player.name + ", your hand is bust")
          false
        } else {
          true
        }
      case "s" => false
      case _ => true
    }
  }

  def start(round: Round): Unit = {
    println("New Round started")

    /**
      * Das Blackjack-Spiel beginnt mit dem Einsatz der Spieler.
      */
    BetTui.start(round)

    /**
      * Dann beginnt das Blackjack-Spiel mit dem Austeilen der Karten.
      * Dealer fragt jeden Spieler von links nach rechts, wie sie fortfahren möchten. Abhängig von ihren Karten können
      * Spieler entweder eine weitere Karte fordern (hit) oder sie können weitere Karten ablehnen (stand).  Spieler
      * können so viele weitere Karten fordern, wie sie möchten, aber sobald sie die 21 überschreiten, ist die Hand
      * “bust” und der Einsatz wird vom Dealer eingesammelt.
      */
    for (player <- round.getPlayers) {
      if (!player.isInstanceOf[BankPlayer]) {
        var continue: Boolean = true
        while (continue) {
          printVisibleCardsOfPlayers(round)
          println("Player " + player.name + ", what do you want to do?")
          printMenu
          continue = proccessUserInput(StdIn.readLine(), round, player)
        }
      }
    }
    round.finish()
    printAllCardsAndBetsOfPlayers(round)
    printWinners(round)
  }
}

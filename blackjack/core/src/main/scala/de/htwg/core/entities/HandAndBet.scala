package de.htwg.core.entities

import play.api.libs.json.{Writes, Json}

/**
  * Created by Michael Meister on 18.01.2016.
  */
case class HandAndBet(private val hand: Hand, private val bet: Bet) {

  def _1: Hand = {
    hand
  }

  def _2: Bet = {
    bet
  }

}

object HandAndBet {

  implicit val handAndBetWrites = new Writes[HandAndBet] {
    def writes(handAndBet: HandAndBet) = Json.obj(
      "hand" -> handAndBet.hand,
      "bet" -> handAndBet.bet
    )
  }

  implicit val handAndBetReads = Json.reads[HandAndBet]

}

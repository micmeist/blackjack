package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by Michael Meister on 23.01.2016.
  */
class RoundPlayerTest extends FlatSpec with Matchers with BeforeAndAfter {

  var bank: BankPlayer = null
  var roundPlayer: RoundPlayer = null

  before {
    bank = new BankPlayer()
  }

  "RoundPlayer as a bank" should "has to hit when sum of cards is 16" in {
    roundPlayer = RoundPlayer(bank, TestUtilites.getTestHandBank(Array(10, 6)), new Bet(0))
    roundPlayer.hasToHit should be(true)
  }

  it should "has not to hit when sum of cards is 17" in {
    roundPlayer = RoundPlayer(bank, TestUtilites.getTestHandBank(Array(10, 7)), new Bet(0))
    roundPlayer.hasToHit should be(false)
  }

  it should "has not to hit when sum of cards is 18" in {
    roundPlayer = RoundPlayer(bank, TestUtilites.getTestHandBank(Array(10, 8)), new Bet(0))
    roundPlayer.hasToHit should be(false)
  }

}

package de.htwg.core.entities

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by Michael Meister on 24.01.2016.
  */
class BetTest extends FlatSpec with Matchers {

  behavior of "Bet amount"

  it should "raise 10 when add 10" in {
    (new Bet(0) + 10).amount should be(10)
  }

}

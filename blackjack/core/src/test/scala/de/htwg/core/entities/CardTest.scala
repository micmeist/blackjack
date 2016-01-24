package de.htwg.core.entities

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by Michael Meister on 24.01.2016.
  */
class CardTest extends FlatSpec with Matchers {

  "A card " should "retrun its name and color" in {
    val card: Card = new Card("testA", "testB", 10)
    card.toString.contains("testA") should be(true)
    card.toString.contains("testB") should be(true)
  }

}

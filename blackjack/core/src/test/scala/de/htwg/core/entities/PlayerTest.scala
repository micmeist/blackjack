package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by Michael Meister on 09.01.2016.
  */
class PlayerTest extends FlatSpec with Matchers with BeforeAndAfter {

  var bank: BankPlayer = null
  var human: HumanPlayer = null

  before {
    bank = new BankPlayer
    human = new HumanPlayer
  }

  "Banks money" should "be reduced when subtraction is used" in {
    (bank - 100).money should be(bank.money - 100)
  }

  it should "be raised when negative subtraction is used" in {
    (bank - (-100)).money should be(bank.money + 100)
  }

  it should "be raised when addition is used" in {
    (bank + 100).money should be(bank.money + 100)
  }

  it should "be reduced when negative addition is used" in {
    (bank + (-100)).money should be(bank.money - 100)
  }

  "A human players money" should "be reduced when subtraction is used" in {
    (human - 100).money should be(human.money - 100)
  }

  it should "be raised when negative subtraction is used" in {
    (human - (-100)).money should be(human.money + 100)
  }

  it should "be raised when addition is used" in {
    (human + 100).money should be(human.money + 100)
  }

  it should "be reduced when negative addition is used" in {
    (human + (-100)).money should be(human.money - 100)
  }

}

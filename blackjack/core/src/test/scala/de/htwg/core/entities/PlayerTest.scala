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
    val money = bank.getMoney
    bank - 100.10
    bank.getMoney should be (money - 100.10)
  }

  it should "be raised when negative subtraction is used" in {
    val money = bank.getMoney
    bank - (-100.10)
    bank.getMoney should be (money + 100.10)
  }

  it should "be raised when addition is used" in {
    val money = bank.getMoney
    bank + 100.10
    bank.getMoney should be (money + 100.10)
  }

  it should "be reduced when negative addition is used" in {
    val money = bank.getMoney
    bank + (-100.10)
    bank.getMoney should be (money - 100.10)
  }

  "A human players money" should "be reduced when subtraction is used" in {
    val money = human.getMoney
    human - 100.10
    human.getMoney should be (money - 100.10)
  }

  it should "be raised when negative subtraction is used" in {
    val money = human.getMoney
    human - (-100.10)
    human.getMoney should be (money + 100.10)
  }

  it should "be raised when addition is used" in {
    val money = human.getMoney
    human + 100.10
    human.getMoney should be (money + 100.10)
  }

  it should "be reduced when negative addition is used" in {
    val money = human.getMoney
    human + (-100.10)
    human.getMoney should be (money - 100.10)
  }

}

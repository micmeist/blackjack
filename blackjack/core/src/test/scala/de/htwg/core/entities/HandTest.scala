package de.htwg.core.entities

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by Michael Meister on 21.12.2015.
  */
class HandTest extends FlatSpec with Matchers with BeforeAndAfter {

  var hand: Hand = null

  def executeTest(test: () => Unit): Unit = {
    hand = new HandHumanPlayer
    test()

    hand = new HandBank
    test()
  }

  private def getTestCard(weight: Int): Card = {
    new Card("Test", "Test", weight)
  }

  "An empty hand of Bank" should "not be too high" in {
    executeTest(() => {
      hand.isBust should be(false)
    })
  }

  "An empty hand of Player" should "not be too high" in {
    executeTest(() => {
      hand.isBust should be(false)
    })
  }

  "The hand 10,10" should "not be too high" in {
    executeTest(() => {
      hand.cards = List(getTestCard(10), getTestCard(10))
      hand.isBust should be(false)
    })
  }

  "The hand 10,10,1" should "not be too high" in {
    executeTest(() => {
      hand.cards = List(getTestCard(10), getTestCard(10), getTestCard(1))
      hand.isBust should be(false)
    })
  }

  "The hand 10,10,2" should "be too high" in {
    executeTest(() => {
      hand.cards = List(getTestCard(10), getTestCard(10), getTestCard(2))
      hand.isBust should be(true)
    })
  }

  "A Players hand" should "be fully visible" in {
    val cardOne = new Card("Kreuz", "K", 10)
    val cardTwo = new Card("Pik", "K", 10)
    val hand: Hand = new HandHumanPlayer
    hand.addCardToHand(cardOne)
    hand.addCardToHand(cardTwo)
    hand.getVisibleCards() should be(List(cardTwo, cardOne))
  }
  it should "have the second card on top" in {
    val cardOne = new Card("Kreuz", "K", 10)
    val cardTwo = new Card("Pik", "K", 10)
    val hand: Hand = new HandHumanPlayer
    hand.addCardToHand(cardOne)
    hand.addCardToHand(cardTwo)
    hand.getVisibleCards().head should be(cardTwo)
  }

  "A Banks hand" should "have the second card on top" in {
    val cardOne = new Card("Kreuz", "K", 10)
    val cardTwo = new Card("Pik", "K", 10)
    val hand: Hand = new HandBank
    hand.addCardToHand(cardOne)
    hand.addCardToHand(cardTwo)
    hand.getVisibleCards().head should be(cardTwo)
  }

  it should "not be fully visible" in {
    val cardOne = new Card("Kreuz", "K", 10)
    val cardTwo = new Card("Pik", "K", 10)
    val hand: Hand = new HandBank
    hand.addCardToHand(cardOne)
    hand.addCardToHand(cardTwo)
    hand.getVisibleCards()(1) should not be(cardOne)
    hand.getVisibleCards().head should be(cardTwo)
  }

}

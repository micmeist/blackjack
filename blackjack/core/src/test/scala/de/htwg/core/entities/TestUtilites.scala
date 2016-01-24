package de.htwg.core.entities

/**
  * Created by Michael Meister on 23.12.2015.
  */
object TestUtilites {
  def getTestCard(weight: Int): Card = {
    new Card("Test", "Test", weight)
  }

  def getTestHandBank(cardWeights: Array[Int]): HandBank = {
    val bankHand: HandBank = new HandBank
    addTestCardsToHand(cardWeights, bankHand).asInstanceOf[HandBank]
  }

  def getTestHandHumanPlayer(cardWeights: Array[Int]): HandHumanPlayer = {
    val playerHand: HandHumanPlayer = new HandHumanPlayer
    addTestCardsToHand(cardWeights, playerHand).asInstanceOf[HandHumanPlayer]
  }

  def addTestCardsToHand(cardWeights: Array[Int], hand: Hand): Hand = {
    var resultHand: Hand = hand
    cardWeights.foreach(weight => resultHand = resultHand.addCardToHand(getTestCard(weight)))
    resultHand
  }

}

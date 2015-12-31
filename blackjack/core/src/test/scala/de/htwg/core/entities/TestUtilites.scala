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
    addTestCardsToHand(cardWeights, bankHand)
    bankHand
  }

  def getTestHandHumanPlayer(cardWeights: Array[Int]): HandHumanPlayer = {
    val playerHand : HandHumanPlayer = new HandHumanPlayer
    addTestCardsToHand(cardWeights, playerHand)
    playerHand
  }

  def addTestCardsToHand(cardWeights: Array[Int], hand: Hand): Unit ={
    cardWeights.foreach(weight => hand.addCardToHand(getTestCard(weight)))
  }

}

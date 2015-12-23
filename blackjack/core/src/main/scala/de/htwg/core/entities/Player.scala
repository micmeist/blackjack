package de.htwg.core.entities

/**
 * Created by micmeist on 29.10.2015.
 */
abstract class Player {

  val name: String

}

class HumanPlayer extends Player {

  override val name = "Human player"

}

class BankPlayer extends Player {

  override val name: String = "Bank"

}

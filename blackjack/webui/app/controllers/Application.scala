package controllers

import de.htwg.core.GameCoreController
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def newGame = Action {
    val game = GameCoreController.startNewGame
    Ok(game.toString)
  }

}
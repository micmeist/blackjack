package controllers

import de.htwg.core.GameCoreController
import de.htwg.core.entities.Game
import play.api.mvc._

object Application extends Controller {

  val keyGame = "game"

  def index = Action {
    Ok(views.html.index())
  }

  def startNewGame = Action {
    val game: Game = GameCoreController.startNewGame
    Ok(views.html.game(game)).withCookies(new Cookie(keyGame, "keks"))
  }

  def startNewRound = Action { request =>
    Ok(views.html.round(request.cookies.get(keyGame).get.value))
  }
}
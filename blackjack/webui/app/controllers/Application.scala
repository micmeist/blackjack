package controllers

import de.htwg.core.GameCoreController
import de.htwg.core.entities.Game
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }

  def startNewGame = Action {
    val game: Game = GameCoreController.startNewGame
    Ok(views.html.game(game)).withSession("session_game" -> game.toString)
  }

  def startNewRound() = Action { request =>
    request.session.get("session_game").map { session_game =>
      Ok(views.html.round("Hello " + session_game))
    }.getOrElse {
      Unauthorized("Oops, you should not be here")
    }
  }
}
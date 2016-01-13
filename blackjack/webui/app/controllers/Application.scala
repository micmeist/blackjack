package controllers

import de.htwg.core.GameCoreController
import play.api.libs.json.Json
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def newGame = Action {
    val game = GameCoreController.startNewGame
    Ok(Json.stringify(Json.toJson(game)))
  }

  def newRound = Action {
    //TODO: implement
    Ok("new round not implemented yet")
  }

}
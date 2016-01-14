package controllers

import de.htwg.core.GameCoreController
import de.htwg.core.entities.Game
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def newGame = Action {
    val game = GameCoreController.startNewGame
    Ok(Json.stringify(Json.toJson(game)))
  }

  def newRound = Action(parse.json) { request =>
    request.body.validate[Game].map {
      case (game) => Ok("Alles gut")
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatForm(e))
    }

  }

}